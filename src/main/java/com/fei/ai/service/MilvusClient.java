//package com.fei.ai.service;
//
//
//import io.milvus.v2.client.ConnectConfig;
//import io.milvus.v2.client.MilvusClientV2;
//import io.milvus.v2.common.DataType;
//import io.milvus.v2.common.IndexParam;
//import io.milvus.v2.service.collection.request.AddFieldReq;
//import io.milvus.v2.service.collection.request.CreateCollectionReq;
//import io.milvus.v2.service.collection.request.GetLoadStateReq;
//import io.milvus.v2.service.collection.response.ListCollectionsResp;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author bytedance
// * @date 2026/3/4
// **/
//public class MilvusClient {
//
//    String CLUSTER_ENDPOINT = "http://localhost:19530";
//    String TOKEN = "root:Milvus";
//
//    public void createCollection() {
//        // 1. Connect to Milvus server
//        ConnectConfig connectConfig = ConnectConfig.builder().uri(CLUSTER_ENDPOINT).token(TOKEN).build();
//
//        MilvusClientV2 client = new MilvusClientV2(connectConfig);
//
//        // 3. Create a collection in customized setup mode
//
//        // 3.1 Create schema
//        CreateCollectionReq.CollectionSchema schema = client.createSchema();
//
//        // 3.2 Add fields to schema
//        schema.addField(AddFieldReq.builder().fieldName("my_id").dataType(DataType.Int64).isPrimaryKey(true).autoID(false).build());
//
//        schema.addField(AddFieldReq.builder().fieldName("my_vector").dataType(DataType.FloatVector).dimension(5).build());
//
//        schema.addField(AddFieldReq.builder().fieldName("my_varchar").dataType(DataType.VarChar).maxLength(512).build());
//
//// 3.3 Prepare index parameters
//        IndexParam indexParamForIdField = IndexParam.builder()
//                .fieldName("my_id")
//                .indexType(IndexParam.IndexType.AUTOINDEX)
//                .build();
//
//        IndexParam indexParamForVectorField = IndexParam.builder()
//                .fieldName("my_vector")
//                .indexType(IndexParam.IndexType.AUTOINDEX)
//                .metricType(IndexParam.MetricType.COSINE)
//                .build();
//
//        List<IndexParam> indexParams = new ArrayList<>();
//        indexParams.add(indexParamForIdField);
//        indexParams.add(indexParamForVectorField);
//
//        CreateCollectionReq customizedSetupReq1 = CreateCollectionReq.builder()
//                .collectionName("customized_setup_1")
//                .collectionSchema(schema)
//                .indexParams(indexParams)
//                .build();
//
//        client.createCollection(customizedSetupReq1);
//
//        // 3.5 Get load state of the collection
//        GetLoadStateReq customSetupLoadStateReq1 = GetLoadStateReq.builder()
//                .collectionName("customized_setup_1")
//                .build();
//
//        Boolean loaded = client.getLoadState(customSetupLoadStateReq1);
//        System.out.println(loaded);
//    }
//
//    public void queryCollections() {
//        ConnectConfig connectConfig = ConnectConfig.builder()
//                .uri(CLUSTER_ENDPOINT)
//                .token(TOKEN)
//                .build();
//
//        MilvusClientV2 client = new MilvusClientV2(connectConfig);
//
//        ListCollectionsResp resp = client.listCollections();
//        System.out.println(resp.getCollectionNames());
//    }
//
//    public static void main(String[] args) {
//        MilvusClient main = new MilvusClient();
//        // 创建 collection
////        main.createCollection();
//
//        // 查询 collection
//        main.queryCollections();
//    }
//}
