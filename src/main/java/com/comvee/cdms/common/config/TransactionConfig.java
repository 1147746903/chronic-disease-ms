package com.comvee.cdms.common.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

@Aspect
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    private static final String ASPECTJ_EXPRESSION = "execution(* com.comvee.cdms.*.service..*Service*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {

        RuleBasedTransactionAttribute txAttr_LOCK = new RuleBasedTransactionAttribute();
        txAttr_LOCK.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr_LOCK.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        txAttr_LOCK.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

        RuleBasedTransactionAttribute txAttr_DEFAULT = new RuleBasedTransactionAttribute();
        txAttr_DEFAULT.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr_DEFAULT.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("*WithLock", txAttr_LOCK);
        source.addTransactionalMethod("add*", txAttr_DEFAULT);
        source.addTransactionalMethod("update*", txAttr_DEFAULT);
        source.addTransactionalMethod("modify*", txAttr_DEFAULT);

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(ASPECTJ_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
