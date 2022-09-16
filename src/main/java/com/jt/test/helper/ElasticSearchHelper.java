package com.jt.test.helper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.mapping.IntegerNumberProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.get_alias.IndexAliases;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.jt.test.common.HttpResult;
import com.jt.test.domain.entity.Company;
import com.jt.test.domain.vo.IndexInfoVO;
import com.jt.test.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * ElasticSearchHelper
 *
 * @Author: jt
 * @Date: 2022/9/14 10:31
 */
@Service
public class ElasticSearchHelper {
    @Autowired
    private ElasticsearchClient client;
    @Autowired
    private CompanyService companyService;

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public Boolean indexExists(String indexName) throws IOException {
        BooleanResponse index = client.indices().exists(item -> item.index(indexName));
        return index.value();
    }

    /**
     * 判断文档是否存在
     */
    public Boolean docExists(String indexName, String id) throws IOException {
        BooleanResponse doc = client.exists(item -> item.index(indexName).id(id));
        return doc.value();
    }

    /**
     * 创建索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public HttpResult addIndex(String indexName, String numberOfShards, String numberOfReplicas, String refreshInterval) throws IOException {
        //判断是否有相同索引存在，则删除旧的创建新的索引（如果旧索引有文档会被删除）
        if (indexExists(indexName)) {
            DeleteIndexRequest.Builder deleteIndex = new DeleteIndexRequest.Builder().index(indexName);
            client.indices().delete(deleteIndex.build()).acknowledged();
        }
        //创建名为user的索引,设置主分片数量为1，副本分片数量为3；
        //CreateIndexResponse user = client.indices().create(item -> item.index(indexName));
        CreateIndexRequest.Builder request = new CreateIndexRequest.Builder().index(indexName).settings(s -> s.numberOfShards(numberOfShards).numberOfReplicas(numberOfReplicas).refreshInterval(new Time.Builder().time(refreshInterval).build()));
        //设置mapping，这里预设3个，后面插入数据会根据数据自动改变
        HashMap<String, Property> documentMap = new HashMap<>();
        documentMap.put("companyId", Property.of(property ->
                        property.text(TextProperty.of(textProperty ->
                                textProperty.index(true))
                        )
                )
        );
        documentMap.put("name", Property.of(property ->
                        property.text(TextProperty.of(textProperty ->
                                textProperty.index(true))
                        )
                )
        );
        documentMap.put("status", Property.of(property ->
                        property.integer(IntegerNumberProperty.of(integerNumberProperty ->
                                integerNumberProperty.index(true))
                        )
                )
        );
        request.mappings(m -> m.properties(documentMap));
        client.indices().create(request.build()).acknowledged();
        return HttpResult.success("索引创建成功");
    }

    /**
     * 删除索引
     */
    public HttpResult deleteIndex(String indexName) throws IOException {
        if (indexExists(indexName)) {
            DeleteIndexRequest.Builder deleteIndex = new DeleteIndexRequest.Builder().index(indexName);
            client.indices().delete(deleteIndex.build()).acknowledged();
            return HttpResult.success("删除成功");
        } else {
            return HttpResult.failed("索引不存在");
        }
    }

    /**
     * 根据索引名查mapping和setting信息
     */
    public HttpResult getIndex(String indexName) throws IOException {
        if (!indexExists(indexName)) {
            return HttpResult.failed("索引不存在");
        }
        IndexInfoVO indexInfoVO = new IndexInfoVO();
        //构建获取Index的请求---------注意与getAliasRequest的区别
        GetIndexRequest request = new GetIndexRequest.Builder().index(indexName).build();
        //获取目标索引
        GetIndexResponse getIndexResponse = client.indices().get(request);
        //从索引中一步步往下拿想要的值，我们要的properties就是最终取出来的map的key值
        Map<String, IndexState> result = getIndexResponse.result();
        IndexState indexState = result.get(indexName);
        Map<String, Property> properties = indexState.mappings().properties();
        //创建MapList来装map
        List<Map<String, Object>> propertieList = new ArrayList<>();
        if (!properties.isEmpty()) {
            properties.forEach((k, v) -> {
                // 创建一个新map，把所有的properties当作value存进去
                // 如果不放在里面，每次map的put操作由于key是相同的会把之前放进去的value覆盖掉
                Map<String, Object> map = new HashMap<>();
                map.put("propertie", k);
                propertieList.add(map);
            });
        }
        //尝试拿setting里面的值
        IndexSettings settings = indexState.settings().index();

        indexInfoVO.setIndexName(settings.providedName());
        indexInfoVO.setCreationDate(settings.creationDate());
        indexInfoVO.setUuid(settings.uuid());
        indexInfoVO.setNumberOfShards(settings.numberOfShards());
        indexInfoVO.setNumberOfReplicas(settings.numberOfReplicas());
        indexInfoVO.setPropertieList(propertieList);
        return HttpResult.success(indexInfoVO);
    }

    /**
     * 查看所有索引
     */
    public HttpResult indexList() throws IOException {
        GetAliasRequest.Builder request = new GetAliasRequest.Builder();
        GetAliasResponse alias = client.indices().getAlias(request.build());
        Map<String, IndexAliases> result = alias.result();

        List<Map<String, Object>> resultList = new ArrayList<>();
        result.forEach((k, v) -> {
            //除去es默认索引（自带的都是以.开头的）
            if (!k.startsWith(".")) {
                //创建map用来存每个索引名，然后用List保存，下个索引名覆盖map存入新索引名再保存到List，循环
                Map map = new HashMap<>();
                map.put("indexName", k);
                resultList.add(map);
            }
        });
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(resultList));
        return HttpResult.success((long) resultList.size(), resultList);
    }

    /**
     * 批量添加文档
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public HttpResult importDoc(String indexName) throws IOException {
        if (!indexExists(indexName)) {
            return HttpResult.failed("索引不存在");
        }
        List<Company> companyList = companyService.list();
//        IndexResponse response = client.index(x -> x.index(indexName).document(companyList));
        //创建BulkOp列表准备批量插入doc
        ArrayList<BulkOperation> bulkOperations = new ArrayList<>();

//        companyList.forEach(a->bulkOperations.add(BulkOperation.of(b->b.index(c->c.id(a.getCompanyId()).document(a)))));
        for (Company company : companyList) {
            bulkOperations.add(BulkOperation.of(x -> x.index(y -> y.id(company.getCompanyId()).document(company))));
        }
        client.bulk(x -> x.index(indexName).operations(bulkOperations));
        return HttpResult.success("批量添加文档成功");
    }

    /**
     * 根据id批量删除文档
     *
     * @param ids
     * @param indexName
     * @return
     * @throws IOException
     */
    public HttpResult deleteDoc(String ids, String indexName) throws IOException {
        if (!indexExists(indexName)) {
            return HttpResult.failed("索引不存在");
        }
        String[] idArray = ids.split(",");
        List<String> idList = Arrays.asList(idArray);
        List<String> failIdList = new ArrayList<>();
        //判断doc是否存在，将不存在的docId输出
        for (String id : idList) {
            if (!docExists(indexName, id))
                failIdList.add(id);
        }
        if (!failIdList.isEmpty() && failIdList.size() != 0) {
            return HttpResult.failed("删除失败，文档id为：" + failIdList + "的数据不存在");
        }


        ArrayList<BulkOperation> bulkOperations = new ArrayList<>();
        idList.forEach(a -> bulkOperations.add(BulkOperation.of(b -> b.delete(c -> c.id(a)))));
        client.bulk(x -> x.index(indexName).operations(bulkOperations));
        return HttpResult.success("批量删除文档成功");
    }

    /**
     * 通过索引名查看索引文档信息
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public HttpResult getIndexDoc(String indexName, String id) throws IOException {
        if (!indexExists(indexName)) {
            return HttpResult.failed("索引不存在");
        }
        //这个getRequest好像必须要带id()方法，不然会报错缺少property---id
        if (id == null || id.equals("")) {
            return HttpResult.failed("id不能为空,请输入id");
        }
        GetRequest getRequest = new GetRequest.Builder().index(indexName).id(id).build();
        //通过id，indexName拿到文档，用Company实体类来接source数据（doc里面的field即mysql的字段）
        GetResponse<Company> response = client.get(getRequest, Company.class);
        if (response.found()) {
            return HttpResult.success(1L, response.source());
        } else {
            return HttpResult.failed("查询失败，没有数据");
        }
    }


//==================================================================以下是search业务代码====================================================================================================

    /**
     * 分页查询全文档
     */
    public HttpResult list(String indexName, Integer pageNum, Integer pageSize) throws IOException {
        //创建搜索条件，索引名和分页,从pageNum个元素开始（起始下标为0），往后查询pageSize个元素
        SearchRequest searchRequest = new SearchRequest.Builder().index(indexName)
                .from((pageNum - 1) * pageSize).size(pageSize)
                .sort(options -> options.field(sort -> sort.field("time").order(SortOrder.Desc)))
                .build();
        //
        SearchResponse<Company> search = client.search(searchRequest, Company.class);
        List<Company> companyList = dataHandler(search);
        return HttpResult.success(Long.valueOf(companyList.size()), companyList);
    }

    /**
     * 数据过滤，返回指定字段
     */
    public HttpResult filter(String indexName, String includeFields) throws IOException {
        //拆分指定字段
        String[] includeFieldsArray = includeFields.split(",");
        List<String> includeFieldList = Arrays.asList(includeFieldsArray);
        //建立搜索条件
        SearchRequest request = new SearchRequest.Builder().index(indexName)
                .source(config -> config
                        .filter(filter -> filter.includes(includeFieldList))).build();
        //查询
        SearchResponse<Company> search = client.search(request, Company.class);
        List<Company> companyList = dataHandler(search);

        return HttpResult.success(companyList);
        //链式写法
////        SearchResponse<Company> searchResponse = client.search(srBuilder -> srBuilder
////                .index("test_company_1")
////                // 执行要返回的字段
////                .source(sourceConfigBuilder -> sourceConfigBuilder
////                        .filter(sourceFilterBuilder -> sourceFilterBuilder
////                                .includes(includeFieldList))), Company.class);
////
////        //解析查询结果
////        List<Hit<Company>> hits = searchResponse.hits().hits();
////        List<Company> companyList = new ArrayList<>();
////        for (Hit<Company> hit : hits) {
////            Company source = hit.source();
////            companyList.add(source);
////        }
//
//        return HttpResult.success(companyList);
    }

    /**
     * match查找，关键字分词
     */
    public HttpResult match(String indexName, String field, String keyWord, Integer pageNum, Integer pageSize) throws IOException {
        if (!indexExists(indexName)) {
            return HttpResult.failed("索引不存在");
        }
        SearchResponse<Company> search = client.search(request -> request
                        .index(indexName)
                        .from((pageNum - 1) * pageSize).size(pageSize)
                        .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                        .query(q -> q
                                .match(m -> m
                                        .field(field).query(FieldValue.of(keyWord))))

                , Company.class);
        List<Company> companyList = dataHandler(search);
        return HttpResult.success(Long.valueOf(companyList.size()), companyList);
    }

    /**
     * 命中数据封装到实体类处理
     *
     * @param search
     * @return
     */
    private List<Company> dataHandler(SearchResponse<Company> search) {
        List<Hit<Company>> hits = search.hits().hits();
        List<Company> companyList = new ArrayList<>();
        for (Hit<Company> hit : hits) {
            Company source = hit.source();
            companyList.add(source);
        }
        return companyList;
    }
}
