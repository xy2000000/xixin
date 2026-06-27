package com.fxy.xixin.ai.rag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KnowledgeBaseConfig {

    private final ResourcePatternResolver resourcePatternResolver;

    @Bean
    @Qualifier("knowledgeVectorStore")
    public VectorStore knowledgeVectorStore(org.springframework.ai.embedding.EmbeddingModel embeddingModel) {
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();
        List<Document> documents = loadDocuments();
        if (documents.isEmpty()) {
            log.warn("未找到知识库文档");
            return store;
        }
        TokenTextSplitter splitter = new TokenTextSplitter(500, 100, 5, 5000, true);
        List<Document> chunks = splitter.apply(documents);
        store.add(chunks);
        log.info("知识库初始化完成：{} 个文档 → {} 个片段", documents.size(), chunks.size());
        return store;
    }

    private List<Document> loadDocuments() {
        List<Document> allDocs = new ArrayList<>();
        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath:rag/*.md");
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename == null) continue;
                log.info("加载知识库文档: {}", filename);
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)
                        .withIncludeCodeBlock(false)
                        .withIncludeBlockquote(false)
                        .withAdditionalMetadata("source", filename)
                        .build();
                MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
                allDocs.addAll(reader.get());
            }
        } catch (IOException e) {
            log.error("知识库文档加载失败", e);
        }
        return allDocs;
    }
}