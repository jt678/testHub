package com.jt.test.demo1.helper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.get_alias.IndexAliases;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.domain.bo.ElasticSearchBO;
import com.jt.test.demo1.domain.entity.Company;
import com.jt.test.demo1.domain.vo.IndexInfoVO;
import com.jt.test.demo1.service.CompanyService;
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
        //设置mapping，这里预设3个，后面插入数据会根据数据自动填充其他字段mapping
        //这里发现一个错误，因为创建索引时预设了三个mapping但是没有设置映射keyword，在term精确查询中需要用到.keyword，但是在后面导入数据时因为有这几个字段，所以并没有给它们再创建mapping而是用了初始化的设置所以查不到
        HashMap<String, Property> documentMap = new HashMap<>();
//        documentMap.put("companyId", Property.of(property ->
//                        property.text(TextProperty.of(textProperty ->
//                                textProperty.index(true))
//                        )
//                )
//        );
//        documentMap.put("name", Property.of(property ->
//                        property.text(TextProperty.of(textProperty ->
//                                textProperty.index(true))
//                        )
//                )
//        );
//        documentMap.put("status", Property.of(property ->
//                        property.integer(IntegerNumberProperty.of(integerNumberProperty ->
//                                integerNumberProperty.index(true))
//                        )
//                )
//        );
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

        //纯链式写法
        //companyList.forEach(a->bulkOperations.add(BulkOperation.of(b->b.index(c->c.id(a.getCompanyId()).document(a)))));
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
        //与ids方法类似，但是query方法里面封装了ids方法，可以输入多id
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
    public HttpResult list(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        //创建搜索条件，索引名和分页,从pageNum个元素开始（起始下标为0），往后查询pageSize个元素
        SearchRequest searchRequest = new SearchRequest.Builder().index(bo.getIndexName())
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
    public HttpResult filter(ElasticSearchBO bo) throws IOException {
        //拆分指定字段
        String[] includeFieldsArray = bo.getFields().split(",");
        List<String> includeFieldList = Arrays.asList(includeFieldsArray);
        //建立搜索条件
        SearchRequest request = new SearchRequest.Builder().index(bo.getIndexName())
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
    public HttpResult match(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        if (!indexExists(bo.getIndexName())) {
            return HttpResult.failed("索引不存在");
        }
        //多字段查询在qurey里面用multi_match（m->m.fields(xx,xx).query(xx).operator(Operator.xx)）
        SearchResponse<Company> search = client.search(request -> request
                        .index(bo.getIndexName())
                        .from((pageNum - 1) * pageSize).size(pageSize)
                        .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                        .query(q -> q
                                .match(m -> m
                                        .field(bo.getField()).query(FieldValue.of(bo.getKeyWord()))))

                , Company.class);
        List<Company> companyList = dataHandler(search);
        return HttpResult.success(Long.valueOf(companyList.size()), companyList);
    }

    /**
     * Term精确查询
     */
    public HttpResult term(ElasticSearchBO bo) throws IOException {
        //处理field,如果输入的不是keyword就转成keyword再去查询
        String field = bo.getField();
        String keyWord = bo.getKeyWord();
        if (!field.endsWith(".keyword")) {
            field = field + ".keyword";
        }
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        //这里是lambda表达式的一个约束，引用的变量必须是最终变量
        String finalField = field;
        //此处精确搜索需要用field.keyWord,因为存入时field进行了分词存入，无法找到此字段真实性，所以要用映射
        SearchResponse<Company> search = client.search(request -> request
                        .index(bo.getIndexName())
                        .from((pageNum - 1) * pageSize).size(pageSize)
                        .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                        .query(q -> q
                                .term(t -> t
                                        .field(finalField)
                                        .value(FieldValue.of(keyWord))))
                , Company.class
        );
        List<Company> companyList = dataHandler(search);
        return HttpResult.success(Long.valueOf(companyList.size()), companyList);
    }

    /**
     * range范围查询（time目前不可用。报错）
     */
    public HttpResult range(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        //此处日期目前还不可用
        if (bo.getField().equals("time")) {
            SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                            .from((pageNum - 1) * pageSize).size(pageSize)
                            .query(q -> q.range(r -> r.field(bo.getField()).lt(JsonData.of("now‐2y"))))
                            .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                    , Company.class);
            List<Company> companyList = dataHandler(search);
            return HttpResult.success(Long.valueOf(companyList.size()), companyList);
        }
        //如果是不是日期
        else {
            SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                            .from((pageNum - 1) * pageSize).size(pageSize)
                            .query(q -> q.range(r -> r.field(bo.getField()).gte(JsonData.of(5)).lte(JsonData.of(15))))
                            .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                    , Company.class);
            List<Company> companyList = dataHandler(search);
            return HttpResult.success(Long.valueOf(companyList.size()), companyList);
        }
    }

    /**
     * fuzzy模糊查询
     */
    public HttpResult fuzzy(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                        .query(q -> q.fuzzy(f -> f.field(bo.getField())
                                .value(FieldValue.of(bo.getKeyWord()))
                                //这个参数表示误差长度。如果设置成auto会根据查询词的长度定义距离
                                .fuzziness("auto")))
                        .from((pageNum - 1) * pageSize).size(pageSize)
                        .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                , Company.class);
        List<Company> companyList = dataHandler(search);
        return HttpResult.success(Long.valueOf(companyList.size()), companyList);
    }

    /**
     * 多个id查询
     */
    public HttpResult ids(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        //处理ids
        List<String> idList = Arrays.asList(bo.getIds().split(","));
        SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                        .query(q -> q.ids(id -> id.values(idList)))
                        .from((pageNum - 1) * pageSize).size(pageSize)
                        .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                , Company.class);
        List<Company> companyList = dataHandler(search);
        return HttpResult.success(Long.valueOf(companyList.size()), companyList);
    }

    /**
     * （重点）高亮查询
     */
    public HttpResult highLight(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        //处理fields
        List<String> fieldList = Arrays.asList(bo.getFields().split(","));
        //先进行常规搜索,且高亮字段必须为2个
        if (indexExists(bo.getIndexName()) && fieldList.size() == 2) {
            SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                            .query(q -> q.multiMatch(m -> m.fields(fieldList).query(bo.getKeyWord()).operator(Operator.Or)))
                            .from((pageNum - 1) * pageSize).size(pageSize)
                            .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                            //高亮
                            .highlight(light -> light.fields(fieldList.get(0), lightfield -> lightfield)
                                    .fields(fieldList.get(1), lightfield -> lightfield)
                                    .preTags("<font color='red'>")
                                    .postTags("</font>")
                                    //多字段查询时，需要设置false
                                    //如果置为true，除非该字段的查询结果不为空才会被高亮。它的默认值是false，意味 着它可能匹配某个字段却高亮一个不同的字段
                                    .requireFieldMatch(false))
                    , Company.class);
            List<Map<String, List<String>>> companyList = new ArrayList<>();
            List<Hit<Company>> hitList = search.hits().hits();
            for (Hit<Company> hit : hitList) {
                Map<String, List<String>> highlight = hit.highlight();
                companyList.add(highlight);
            }

            return HttpResult.success(Long.valueOf(companyList.size()), companyList);
        } else
            return HttpResult.failed("查询失败：请确认索引是否存在，且字段必须为2个！");
    }

    /**
     * 布尔查询Bool
     */
    public HttpResult booleanSearch(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                        .from((pageNum - 1) * pageSize).size(pageSize)
                        .query(queryBuilder -> queryBuilder
                                .bool(boolQueryBuilder -> boolQueryBuilder
                                        // and
                                        //.must(queryBuilder2 -> queryBuilder2
                                        //        .range(rangeBuilder -> rangeBuilder.field("orderNum").gte(JsonData.of(4))))
                                        //.must(queryBuilder2 -> queryBuilder2
                                        //        .match(matchQueryBuilder -> matchQueryBuilder.field("name").query(FieldValue.of(keyWord))))
                                        //or
                                        .should(queryBuilder2 -> queryBuilder2
                                                .range(rangeBuilder -> rangeBuilder.field("orderNum").lte(JsonData.of(4))))
                                        .should(queryBuilder2 -> queryBuilder2
                                                .match(matchQueryBuilder -> matchQueryBuilder.field("name").query(FieldValue.of(bo.getKeyWord()))))
                                )
                        )
                , Company.class);
        List<Company> companyList = dataHandler(search);
        return HttpResult.success(Long.valueOf(companyList.size()), companyList);
    }


//==================================================================以下是Aggregation(聚合)业务代码====================================================================================================

    /**
     * 查max,min,avg（单个）
     */
    public HttpResult common(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                        .aggregations("max", agg -> agg.max(max -> max.field(bo.getField())))
                        .aggregations("min", agg -> agg.min(min -> min.field(bo.getField())))
                        .aggregations("avg", agg -> agg.avg(avg -> avg.field(bo.getField())))
                        .from((pageNum - 1) * pageSize).size(pageSize)
                        .sort(option -> option.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                , Company.class);
        Map<String, Aggregate> aggregations = search.aggregations();
        double avg = aggregations.get("avg").avg().value();
        double min = aggregations.get("min").min().value();
        double max = aggregations.get("max").max().value();
        return HttpResult.success("最小值：" + min + ",最大值：" + max + ",平均值：" + avg);
    }

    /**
     * 统计（所有的如max，min，sum都查出来）
     */
    public HttpResult count(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                        .aggregations("count", agg -> agg.stats(s -> s.field(bo.getField())))
                        .from((pageNum - 1) * pageSize).size(pageSize)
                        .sort(s -> s.field(sort -> sort.field("orderNum").order(SortOrder.Asc)))
                , Company.class);
        Map<String, Aggregate> aggregations = search.aggregations();
        StatsAggregate all = aggregations.get("count").stats();

        long count = all.count();
        double max = all.max();
        double min = all.min();
        double avg = all.avg();
        double sum = all.sum();

        return HttpResult.success("max:" + max + ";min:" + min + ";avg:" + avg + ";sum:" + sum + ";count:" + count);
    }

    /**
     * 分类+去重
     *
     * @param bo
     * @return
     */
    public HttpResult group(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        //todo：这里有个问题，只能取前面十个的groupby，其他的取不到，且如果用time来group不能使用.keyword要使用lterms
        SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                        //获取字段的分类信息
                        .aggregations("group", agg -> agg
                                .terms(t -> t
                                        .field(bo.getField() + ".keyword")))
                        .from((pageNum - 1) * pageSize).size(pageSize)
                , Company.class);
        Aggregate group = search.aggregations().get("group");
        StringTermsAggregate sterms = group.sterms();
        List<StringTermsBucket> termsList = sterms.buckets().array();
        HashMap<String, Long> hashMap = new HashMap<>();
        for (StringTermsBucket term : termsList) {
            hashMap.put(term.key(), term.docCount());
        }


//        termsList.forEach(item -> {
//            System.out.println("key:" + item.key());
//            System.out.println("docCount:" + item.docCount());
//        });
        long total = sterms.sumOtherDocCount() + termsList.size();
        return HttpResult.success(total, hashMap);
    }

    /**
     * 去重
     */
    public HttpResult cardinate(ElasticSearchBO bo) throws IOException {
        int pageNum = bo.getPageNum().intValue();
        int pageSize = bo.getPageSize().intValue();
        SearchResponse<Company> search = client.search(request -> request.index(bo.getIndexName())
                        .aggregations("cardinate", agg -> agg.cardinality(c -> c.field(bo.getField())))
                        .from((pageNum - 1) * pageSize).size(pageSize)
                , Company.class);
        CardinalityAggregate cardinate = search.aggregations().get("cardinate").cardinality();
        long value = cardinate.value();

        return HttpResult.success(value);
    }
}
