package net.bilberry.spring;

import net.bilberry.annotations.SolrQueryMapper;
import net.bilberry.annotations.SolrSearchResultsRows;
import net.bilberry.candidate.model.Candidate;
import net.bilberry.spring.model.SolrModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.core.MethodParameter;
import org.springframework.data.solr.core.query.*;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;
import static org.springframework.util.ReflectionUtils.findField;

public class SolrQueryFromSearchArgumentsMapResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        if (SolrModel.class.equals(methodParameter.getParameterType()))
            return true;

        if (methodParameter.hasParameterAnnotation(SolrQueryMapper.class)) {
            throw new IllegalArgumentException(String.format("Parameter at position %s must be of type SolrQueryMapper but was %s.",
                    methodParameter.getParameterIndex(), methodParameter.getParameterType()));
        }

        return false;
    }

    @Override
    public SolrModel resolveArgument(final MethodParameter methodParameter, final ModelAndViewContainer modelAndViewContainer, final NativeWebRequest nativeWebRequest, final WebDataBinderFactory webDataBinderFactory) throws Exception {
        return convertEntity(Candidate.class, nativeWebRequest);
    }

    private SolrModel convertEntity(Class<?> entityRoot, final NativeWebRequest nativeWebRequest) {
        SolrModel model = new SolrModel();

        Query query = new SimpleQuery(new Criteria());
        FilterQuery filterQuery = new SimpleFilterQuery();

        nativeWebRequest.getParameterMap().entrySet().stream()
                .filter(p -> findField(entityRoot, p.getKey()) != null
                        && findField(entityRoot, p.getKey()).isAnnotationPresent(org.apache.solr.client.solrj.beans.Field.class))
                .forEach(p -> {
                    String fieldName = findField(entityRoot, p.getKey()).getAnnotation(Field.class).value();
                    filterQuery.addCriteria(new Criteria(StringUtils.isEmpty(fieldName) ? p.getKey(): fieldName).in(p.getValue()));
                });

        query.addFilterQuery(filterQuery).setRows(maxSearchResultRowsFor(entityRoot));

        return model.query(query);
    }

    private int maxSearchResultRowsFor(Class<?> entityRoot) {
        return entityRoot.isAnnotationPresent(SolrSearchResultsRows.class) ?
                getAnnotation(entityRoot, SolrSearchResultsRows.class).value(): 10;
    }
}
