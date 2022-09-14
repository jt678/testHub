package com.jt.test.helper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.mapping.IntegerNumberProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
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
     * 创建索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public HttpResult addIndex(String indexName) throws IOException {
        //判断是否有相同索引存在，则删除旧的创建新的索引
        if (indexExists(indexName)) {
            DeleteIndexRequest.Builder deleteIndex = new DeleteIndexRequest.Builder().index(indexName);
            client.indices().delete(deleteIndex.build()).acknowledged();
        }
        //创建名为user的索引,设置主分片数量为1，副本分片数量为3；
        //CreateIndexResponse user = client.indices().create(item -> item.index(indexName));
        CreateIndexRequest.Builder request = new CreateIndexRequest.Builder().index(indexName).settings(s -> s.numberOfShards("1").numberOfReplicas("3").refreshInterval(new Time.Builder().time("10s").build()));
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
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public Boolean indexExists(String indexName) throws IOException {
        BooleanResponse user = client.indices().exists(item -> item.index(indexName));
        return user.value();
    }

    /**
     * 通过索引名查看索引信息
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public HttpResult getIndex(String indexName, String id) throws IOException {

        GetRequest getRequest = new GetRequest.Builder().index(indexName).id(id).build();
        GetResponse<Company> response = client.get(getRequest, Company.class);
        if (response.found()) {
            return HttpResult.success(1L,response.source());
        } else {
            return HttpResult.failed("查询失败，没有数据");
        }

    }

    /**
     * 查看所有索引信息
     *
     * @return
     */
    public HttpResult getAllIndex() {
        return HttpResult.success();
    }

    /**
     * 批量添加文档
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public HttpResult importDoc(String indexName) throws IOException {
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
     * 批量删除文档
     *
     * @param ids
     * @param indexName
     * @return
     * @throws IOException
     */
    public HttpResult deleteDoc(String ids, String indexName) throws IOException {
        String[] idArray = ids.split(",");
        List<String> idList = Arrays.asList(idArray);
        ArrayList<BulkOperation> bulkOperations = new ArrayList<>();
        idList.forEach(a -> bulkOperations.add(BulkOperation.of(b -> b.delete(c -> c.id(a)))));
        client.bulk(x -> x.index(indexName).operations(bulkOperations));
        return HttpResult.success("批量删除文档成功");
    }
}
