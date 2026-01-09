/*
 * package com.shanzhu.server.ai.config;
 *
 * import com.alibaba.cloud.ai.dashscope.api.DashScopeApi; import
 * com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel; import
 * com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel; import
 * com.baomidou.dynamic.datasource.DynamicRoutingDataSource; import
 * com.shanzhu.api.ai.constant.ModelEnums; import com.shanzhu.api.ai.domain.AiModel;
 * import utils.framework.com.shanzhu.dataself.SpringContextHolder; import
 * constant.ai.server.com.shanzhu.dataself.AIDataSourceConstants; import
 * mapper.ai.server.com.shanzhu.dataself.AiModelMapper; import
 * utils.ai.server.com.shanzhu.dataself.ModelUtils; import
 * org.springframework.ai.embedding.EmbeddingModel; import
 * org.springframework.ai.vectorstore.VectorStore; import
 * org.springframework.ai.vectorstore.pgvector.PgVectorStore; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.jdbc.core.JdbcTemplate;
 *
 * import javax.sql.DataSource;
 *
 */
/**
 * 注入向量数据库能力
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2024-11-23
 *//*
	 *
	 * @Configuration public class VectorStoreConfiguration {
	 *
	 * // TODO 动态注册
	 *
	 * @Value("${spring.ai.dashscope.api-key}") private String aiKey;
	 *
	 */
/**
 * 注册向量数据库
 * @param jdbcTemplate JdbcTemplate
 * @return VectorStore
 *//*
	 *
	 * @Bean public VectorStore vectorStore(JdbcTemplate jdbcTemplate) {
	 * DynamicRoutingDataSource dynamicRoutingDataSource =
	 * SpringContextHolder.getBean(DynamicRoutingDataSource.class); DataSource dataSource
	 * = dynamicRoutingDataSource.getDataSource(AIDataSourceConstants.DS_VECTOR);
	 * jdbcTemplate.setDataSource(dataSource);
	 *
	 * AiModelMapper aiModelMapper = SpringContextHolder.getBean(AiModelMapper.class);
	 * AiModel aiModel =
	 * aiModelMapper.selectAiModelByModelDefault(ModelEnums.ModelTypeEnums.EMBEDDING);
	 * EmbeddingModel embeddingModel = ModelUtils
	 * .modelServiceProviderSelector(aiModel.getModelProvider())
	 * .getEmbeddingModel(aiModel); // TODO 需要动态注册 return
	 * PgVectorStore.builder(jdbcTemplate, embeddingModel) .dimensions(1536) // Optional:
	 * defaults to model dimensions or 1536
	 * .distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE) // Optional: //
	 * defaults to // COSINE_DISTANCE .indexType(PgVectorStore.PgIndexType.HNSW) //
	 * Optional: defaults to HNSW .initializeSchema(true) // Optional: defaults to false
	 * .schemaName("public") // Optional: defaults to "public"
	 * .vectorTableName("ai_vector") // Optional: defaults to "vector_store"
	 * .maxDocumentBatchSize(10000) // Optional: defaults to 10000 .build(); }
	 *
	 * }
	 */
