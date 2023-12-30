/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indexgenesys.skydev.util;




import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin
 */
public class QryBuilder implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(QryBuilder.class.getName());

    private EntityManager em;
    private boolean showLog = false;

    private String qry;
    private String className;

    private String currentQryGroup = null;

    public static enum DDML {

        SELECT, COUNT,
    }

    private List<String> keys = Arrays.asList("SUM", "COUNT", "AVG", "GROUP");

    private List<QryParam> qryParams = new LinkedList<>();
    private Map<String, List<QryParam>> qryMaps = new LinkedHashMap<>();
    private List<QryOrder> ordering = new LinkedList<>();
    private List<String> returnFieldsList = new LinkedList<>();
    private List<String> groupFields = new LinkedList<>();

    private String var = "e";

    private DDML ddml = DDML.SELECT;

    public QryBuilder() {
    }

    public QryBuilder(EntityManager em) {
        this.em = em;
    }

    public QryBuilder(EntityManager em, Class rootClass) {
        this.em = em;
        setQryClass(rootClass);
    }

    public QryBuilder(EntityManager em, Class rootClass, String returnVariable) {
        this.em = em;
        setQryClass(rootClass);
        returnFieldsList.add(var + "." + returnVariable);

    }

    public static QryBuilder get(EntityManager em) {
        return new QryBuilder(em);
    }

    public String getCurrentQryGroup() {
        return currentQryGroup;
    }

    public void setCurrentQryGroup(String currentQryGroup) {
        this.currentQryGroup = currentQryGroup;
    }

    private void setQryClass(Class qryClass) {
        className = qryClass.getSimpleName();
    }

    public QryBuilder add(QryParam param) {
        if (currentQryGroup != null) {
            if (showLog) {
                LOGGER.log(Level.INFO, "Current QryGroup is not null, adding {0}", currentQryGroup);
            }
            param.setQryGroup(currentQryGroup);
        }
        if (showLog) {
            System.out.println("QRY_GROUP = " + currentQryGroup);
        }
        qryParams.add(param);
        return this;
    }

    public void map(Map<String, Object> values) {
        map(values, false);
    }

    public void map(Map<String, Object> values, boolean order) {
        if (values == null) {
            return;
        }

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                addStringQryParam(field, value, QryBuilder.ComparismCriteria.EQUAL);
            } else if (value instanceof Date) {
                addDateParam(field, (Date) value, ComparismCriteria.EQUAL);
            } else {
                addObjectParam(field, value);
            }

            if (order) {
                orderByAsc(field);
            }

        }
    }

    public QryBuilder addStringQryParam(String field, Object value, ComparismCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.String, includeCriteria);
        add(qryParam);
        return this;
    }

    public QryBuilder addStringQryParam(String field, Object value, ComparismCriteria comparismCriteria, IncludeCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.String, comparismCriteria, includeCriteria);
        if (showLog) {
            System.out.println("going to add = " + qryParam);
        }
        add(qryParam);
        return this;
    }

    public QryBuilder addDateParam(String field, Date value, ComparismCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.Date, includeCriteria);
        add(qryParam);
        return this;
    }

    public QryBuilder addNumberParam(String field, Number value, ComparismCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.Number, includeCriteria);
        add(qryParam);
        return this;
    }

    public QryBuilder addDateRange(DateRange dateRange, String fieldName) {
        if (dateRange != null) {
            if (dateRange.getFromDate() != null) {
                addDateParam(fieldName, dateRange.getFromDate(), QryBuilder.ComparismCriteria.GREATER_THAN_OR_EQUAL);
            }
            if (dateRange.getToDate() != null) {
                addDateParam(fieldName, dateRange.getToDate(), QryBuilder.ComparismCriteria.LESS_THAN_OR_EQUAL);
            }
        }
        return this;
    }

    public QryBuilder addRange(Number start, Number end, String field) {

        if (start != null) {
            addNumberParam(field, start, ComparismCriteria.GREATER_THAN_OR_EQUAL);
        }

        if (end != null) {
            addNumberParam(field, end, ComparismCriteria.LESS_THAN_OR_EQUAL);
        }

        return this;
    }

    public QryBuilder addNumberRange(NumberRange numberRange, String field) {
        if (numberRange == null) {
            return this;
        }

        if (numberRange.getMinimum() != null) {
            addNumberParam(field, numberRange.getMinimum(), ComparismCriteria.GREATER_THAN_OR_EQUAL);
        }

        if (numberRange.getMaximum() != null) {
            addNumberParam(field, numberRange.getMaximum(), ComparismCriteria.LESS_THAN_OR_EQUAL);
        }

        return this;
    }

    public QryBuilder addObjectParam(String field, Object value) {
        QryParam qryParam = new QryParam(field, value, FieldType.OBJECT, ComparismCriteria.EQUAL);
        add(qryParam);
        return this;
    }

    public QryBuilder addInParam(String field, Object value) {
        QryParam qryParam = new QryParam(field, value, FieldType.OBJECT, ComparismCriteria.IN);
        add(qryParam);
        return this;
    }

    public QryBuilder addObjectParamNotEqual(String field, Object value) {
        QryParam qryParam = new QryParam(field, value, FieldType.OBJECT, ComparismCriteria.NOT);
        add(qryParam);
        return this;
    }
    public QryBuilder addStringParamNotEqual(String field, Object value) {
        QryParam qryParam = new QryParam(field, value, FieldType.String, ComparismCriteria.NOT);
        add(qryParam);
        return this;
    }

    public QryBuilder addReturnField(String field) {
        returnFieldsList.add(field);
        return this;
    }

    /*
     * the field eg. valueDate
     */
    public QryBuilder addGroupBy(String field) {
        groupFields.add(field);
        return this;
    }

    public QryBuilder orderBy(QryOrder qryOrder) {
        ordering.add(qryOrder);
        return this;
    }

    public QryBuilder orderByAsc(String field) {
        ordering.add(new QryOrder(field, OrderBy.ASC));
        return this;
    }

    public QryBuilder orderByDesc(String field) {
        ordering.add(new QryOrder(field, OrderBy.DESC));
        return this;
    }

    private void buildQryMap() {
        qryMaps.clear();
        for (QryParam qryParam : qryParams) {
            if (!qryMaps.containsKey(qryParam.getQryGroup())) {
                List<QryParam> params = new LinkedList<>();
                qryMaps.put(qryParam.getQryGroup(), params);
            }
            List<QryParam> params = qryMaps.get(qryParam.getQryGroup());
            params.add(qryParam);
        }
    }

    public String getQryInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append(getQry()).append("\n");
        builder.append(getParamString());

        return builder.toString();
    }

    public String getParamString() {
        StringBuilder builder = new StringBuilder();

        for (QryParam qryParam : qryParams) {
            builder.append(qryParam.getField()).append(" : ").append(qryParam.getValue()).append("\n");
        }

        return builder.toString();
    }

    public String getQry() {
        //forming select group

        if (returnFieldsList.isEmpty()) {
            qry = "SELECT e FROM " + className + " e ";
        } else {
            String obj = "";
            int size = returnFieldsList.size();
            int counter = 0;
            for (String object : returnFieldsList) {
                counter++;
                obj += object;

                if (counter != size) {
                    obj += ",";
                }
            }
            qry = "SELECT " + obj + " FROM " + className + " e ";
        }

        buidCriteria();

        if (true) {
            return qry;
        }

        if (qryParams.size() > 0) {
            qry = qry + " WHERE ";
        }

        if (showLog) {
            System.out.println("LIST IS " + qryParams);
            System.out.println("param size = " + qryParams.size());
        }

        buildQryMap();

        int counter = 0;

        for (QryParam qryParam : qryParams) {
            if (qryParam.getValue() == null && qryParam.comparism == ComparismCriteria.NOT) {
                qry += "e." + qryParam.field + " IS NOT NULL ";
            } else if (qryParam.getValue() == null) {
                qry += "e." + qryParam.field + " IS NULL ";
            } else {
                if (qryParam.fieldType == FieldType.String) {
                    if (qryParam.comparism == ComparismCriteria.LIKE) {
                        qry += "e." + qryParam.field + " LIKE '%" + qryParam.value + "%' ";
                    }

                    if (qryParam.comparism == ComparismCriteria.EQUAL) {
                        qry += "e." + qryParam.field + " = '" + qryParam.value + "' ";
                    }
                }
                if (qryParam.fieldType == FieldType.Number) {
                    if (qryParam.comparism != ComparismCriteria.LIKE) {
                        qry += "e." + qryParam.field + " " + qryParam.comparism.getSign() + " " + qryParam.value + " ";
                    }
                }

                if (qryParam.fieldType == FieldType.Date) {
                    qry += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }

                if (qryParam.fieldType == FieldType.OBJECT) {
                    qry += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }
            }

            qryParam.used = true;
            counter++;

            if (counter < qryParams.size()) {
                qry += " AND ";
            }
        }

        //forming group
        int groupCounter = 0;
        int groupSize = groupFields.size();
        if (groupFields.isEmpty() == false) {
            qry += " GROUP BY ";

            for (String groupField : groupFields) {
                qry += " e." + groupField + " ";

                groupCounter++;

                if (groupCounter != groupSize) {
                    qry += " , ";
                }
            }
        }

        //forming order by
        int orderCounter = 0;
        int orderSize = ordering.size();
        if (ordering.isEmpty() == false) {
            qry += " ORDER BY ";

            for (QryOrder qryOrder : ordering) {

                if (qryOrder.orderField.contains("(") && qryOrder.orderField.contains(")")) {
                    qryOrder.addVariable = false;
                }

                if (qryOrder.addVariable) {
                    qry += " " + var + "." + qryOrder.orderField + " " + qryOrder.orderBy.getOrderName();
                } else {
                    qry += " " + qryOrder.orderField + " " + qryOrder.orderBy.getOrderName();
                }

                orderCounter++;

                if (orderCounter != orderSize) {
                    qry += " , ";
                }
            }
        }

        return qry;
    }

    public String buidCriteria() {

        if (qryParams.size() > 0) {
            qry = qry + " WHERE ";
        }

        if (showLog) {
            System.out.println("LIST IS " + qryParams);
            System.out.println("param size = " + qryParams.size());
        }

        buildQryMap();

//            whereGroupCounter = whereGroupCounter + 1;
//            String string = entry.getKey();
//            List<QryParam> groupQryParams = entry.getValue();
//            if(groupQryParams.isEmpty())
//            {
//                continue;
//            }
//            
//            int criteriaParamSize = groupQryParams.size();
        int counter = 0;
//            qry = qry + "(";

        for (QryParam qryParam : qryParams) {
            if (qryParam.getValue() == null && qryParam.comparism == ComparismCriteria.NOT) {
                qry += "e." + qryParam.field + " IS NOT NULL ";
            } else if (qryParam.getValue() == null) {
                qry += "e." + qryParam.field + " IS NULL ";
            } else {
                if (qryParam.fieldType == FieldType.String) {
                    if (qryParam.comparism == ComparismCriteria.LIKE) {
                        qry += "e." + qryParam.field + " LIKE '%" + qryParam.value + "%' ";
                    }

                    if (qryParam.comparism == ComparismCriteria.EQUAL) {
                        qry += "e." + qryParam.field + " = '" + qryParam.value + "' ";
                    }
                }
                if (qryParam.fieldType == FieldType.Number) {
                    if (qryParam.comparism != ComparismCriteria.LIKE) {
                        qry += "e." + qryParam.field + " " + qryParam.comparism.getSign() + " " + qryParam.value + " ";
                    }
                }

                if (qryParam.fieldType == FieldType.Date) {
                    qry += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }

                if (qryParam.fieldType == FieldType.OBJECT) {
                    qry += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }
            }

//                if (counter < criteriaParamSize - 1)
//                {
//                    if(showLog)
//                    {
//                        System.out.println("retrieving param " + counter + " of " + criteriaParamSize);
//                    }
//                    qry += " "+groupQryParams.get(counter + 1).includeCriteria +" ";
//                }
            qryParam.used = true;
            counter++;

            if (counter < qryParams.size()) {
                qry += " AND ";
            }
        }

//            qry = qry + ")";
//            
//            if(whereGroupCounter < numberOfWhereGroups)
//            {
//                qry += " AND ";
//            }
//        }
        //forming group
        int groupCounter = 0;
        int groupSize = groupFields.size();
        if (groupFields.isEmpty() == false) {
            qry += " GROUP BY ";

            for (String groupField : groupFields) {
                qry += " e." + groupField + " ";

                groupCounter++;

                if (groupCounter != groupSize) {
                    qry += " , ";
                }
            }
        }

        //forming order by
        int orderCounter = 0;
        int orderSize = ordering.size();
        if (ordering.isEmpty() == false) {
            qry += " ORDER BY ";

            for (QryOrder qryOrder : ordering) {

                if (qryOrder.orderField.contains("(") && qryOrder.orderField.contains(")")) {
                    qryOrder.addVariable = false;
                }

                if (qryOrder.addVariable) {
                    qry += " " + var + "." + qryOrder.orderField + " " + qryOrder.orderBy.getOrderName();
                } else {
                    qry += " " + qryOrder.orderField + " " + qryOrder.orderBy.getOrderName();
                }

                orderCounter++;

                if (orderCounter != orderSize) {
                    qry += " , ";
                }
            }
        }

        return qry;
    }

    public Query applyParameters(Query query) {

        for (QryParam qryParam : qryParams) {
            query = applyParameters(query, qryParam);
//            if (qryParam.getValue() == null)
//            {
//                continue;
//            }
//            if (qryParam.getFieldType() == FieldType.Date)
//            {
//                query.setParameter(qryParam.paramVar, (Date) qryParam.getValue(), TemporalType.DATE);
//            }
//            if (qryParam.getFieldType() == FieldType.OBJECT)
//            {
//                query.setParameter(qryParam.paramVar, qryParam.getValue());
//            }
        }

        return query;
    }

    public Query applyParameters(Query query, QryParam qryParam) {

        return QryBuilder.applyQueryParameters(query, qryParam);
//            if (qryParam.getValue() == null)
//            {
//                return query;
//            }
//            if (qryParam.getFieldType() == FieldType.Date)
//            {
//                query.setParameter(qryParam.paramVar, (Date) qryParam.getValue(), TemporalType.DATE);
//            }
//            if (qryParam.getFieldType() == FieldType.OBJECT)
//            {
//                query.setParameter(qryParam.paramVar, qryParam.getValue());
//            }
//            
//        return query;
    }

    public static Query applyQueryParameters(Query query, QryParam qryParam) {
        if (qryParam.getValue() == null) {
            return query;
        }
        if (qryParam.getFieldType() == FieldType.Date) {
            query.setParameter(qryParam.paramVar, (Date) qryParam.getValue(), TemporalType.DATE);
        }
        if (qryParam.getFieldType() == FieldType.OBJECT) {
            query.setParameter(qryParam.paramVar, qryParam.getValue());
        }

        return query;
    }

    public int count(Map<String, Object> map) {

        try {
            for (String key : map.keySet()) {
                addObjectParam(key, map.get(key));
            }

            qry = "SELECT COUNT(e) FROM " + className + " e ";

            buidCriteria();

            Query query = em.createQuery(qry);
            applyParameters(query);

            if (showLog) {
                System.out.println(qry);
                System.out.println(getParamString());

            }

            return ObjectValue.get_intValue(query.getSingleResult());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int count() {
        return count(new HashMap<String, Object>());
    }

    public Query buildQry() {

        qry = getQry();

        if (showLog) {
            System.out.println(qry);
        }

        if (em == null) {
            LOGGER.log(Level.SEVERE, "Entity Manager could not be initialised");
        }

        Query query = em.createQuery(qry);

        applyParameters(query);

        return query;
    }

    public void showLog(boolean showLog) {
        this.showLog = showLog;
    }

    public enum ComparismCriteria {

        LIKE(" % "),
        EQUAL(" = "),
        LESS_THAN(" < "),
        GREATER_THAN(" > "),
        GREATER_THAN_OR_EQUAL(" >= "),
        LESS_THAN_OR_EQUAL(" <= "),
        NOT(" != "),
        IN(" IN ");

        ComparismCriteria(String sign) {
            this.sign = sign;
        }

        private String sign;

        private String getSign() {
            return sign;
        }

    }

    public enum IncludeCriteria {

        AND("AND"),
        OR("OR");

        IncludeCriteria(String sign) {
            this.sign = sign;
        }

        private String sign;

        public String getSign() {
            return sign;
        }

    }

    public enum FieldType {
        String, Number, Date, OBJECT
    }

    public static class QryOrder implements Serializable {

        private String orderField;
        private boolean addVariable = true;
        private OrderBy orderBy;

        public QryOrder() {
        }

        public QryOrder(String orderField, OrderBy orderBy) {
            this.orderField = orderField;
            this.orderBy = orderBy;
        }

        public QryOrder(String orderField, OrderBy orderBy, boolean addVariable) {
            this.orderField = orderField;
            this.orderBy = orderBy;
            this.addVariable = addVariable;
        }

    }

    public static class QryParam implements Serializable {

        private Map<String, Object> parameters = null;

        public static final String DEFAULT_QRY_GROUP = "default";
        private boolean used;
        private String field;
        private String qryString = "";
        private Object value;
        private FieldType fieldType;
        private ComparismCriteria comparism;
        private String qryGroup = DEFAULT_QRY_GROUP;
        private IncludeCriteria includeCriteria = IncludeCriteria.AND;

        private String paramVar;
        private QryParam qryParam;

        public QryParam(String field, Object value, FieldType fieldType, ComparismCriteria includeCriteria) {
            this.field = field;
            this.value = value;
            this.fieldType = fieldType;
            this.comparism = includeCriteria;

            paramVar = field.replaceAll("\\.", "") + Math.abs(Objects.hashCode(value));
            qryParam = this;

            build();

        }

        public QryParam(String field, Object value, FieldType fieldType, ComparismCriteria comparismCriteria, IncludeCriteria includeCriteria1) {
            this.field = field;
            this.value = value;
            this.fieldType = fieldType;
            this.comparism = comparismCriteria;
            this.includeCriteria = includeCriteria1;

            paramVar = field.replaceAll("\\.", "") + Math.abs(Objects.hashCode(value));

            qryParam = this;

            build();
        }

        private QryParam(String name, Object value) {
            this.parameters = new HashMap<>();
            this.parameters.put(name, value);

            qryParam = this;

            build();
        }

        public static QryParam with(String name, Object value) {
            return new QryParam(name, value);
        }

        public QryParam and(String name, Object value) {
            this.parameters.put(name, value);
            return this;
        }

        public Map<String, Object> parameters() {
            return this.parameters;
        }

        public boolean isUsed() {
            return used;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public FieldType getFieldType() {
            return fieldType;
        }

        public ComparismCriteria getComparism() {
            return comparism;
        }

        public String getQryGroup() {
            return qryGroup;
        }

        public void setQryGroup(String qryGroup) {
            this.qryGroup = qryGroup;
        }

        public String getQryString() {
            return qryString;
        }

        public void setQryString(String qryString) {
            this.qryString = qryString;
        }

        @Override
        public String toString() {
            return "QryParam{ field=" + field + ", value=" + value + ", comparism=" + comparism + '}';
        }

        public void build() {
            if (qryParam.getValue() == null && qryParam.comparism == ComparismCriteria.NOT) {
                qryString += "e." + qryParam.field + " IS NOT NULL ";
            } else if (qryParam.getValue() == null) {
                qryString += "e." + qryParam.field + " IS NULL ";
            } else {
                if (qryParam.fieldType == FieldType.String) {
                    if (qryParam.comparism == ComparismCriteria.LIKE) {
                        qryString += "e." + qryParam.field + " LIKE '%" + qryParam.value + "%' ";
                    }

                    if (qryParam.comparism == ComparismCriteria.EQUAL) {
                        qryString += "e." + qryParam.field + " = '" + qryParam.value + "' ";
                    }
                }
                if (qryParam.fieldType == FieldType.Number) {
                    if (qryParam.comparism != ComparismCriteria.LIKE) {
                        qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + " " + qryParam.value + " ";
                    }
                }

                if (qryParam.fieldType == FieldType.Date) {
                    qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }

                if (qryParam.fieldType == FieldType.OBJECT) {
                    qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }
            }

        }

    }

    public static class ParamQry {

        private String qryExpression;

        public void dateRangeExpression() {
//            d
        }
    }

    public static enum OrderBy implements Serializable {

        ASC("ASC"), DESC("DESC");
        private String orderName;

        private OrderBy(String orderName) {
            this.orderName = orderName;
        }

        public String getOrderName() {
            return orderName;
        }
    }

    public static class QrySearchOrder implements Serializable {

        private String searchField;
        private OrderBy orderBy;

        public java.lang.String getSearchField() {
            return searchField;
        }

        public void setSearchField(java.lang.String searchField) {
            this.searchField = searchField;
        }

        public OrderBy getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(OrderBy orderBy) {
            this.orderBy = orderBy;
        }

        public static List<QryBuilder.QrySearchOrder> createOrderList(int number) {
            List<QryBuilder.QrySearchOrder> qrySearchOrdersList = new LinkedList<>();
            for (int i = 0; i < number; i++) {
                qrySearchOrdersList.add(new QrySearchOrder());
            }

            return qrySearchOrdersList;
        }

    }

    @SuppressWarnings("unchecked")
    public <T> T getSingleResult(Class<T> t) {
        try {
            List<T> list = buildQry().getResultList();

            if (list != null && list.size() == 1) {
                return list.get(0);
            }
//            else
//            {
//                System.out.println("SingleQryResultList : " + list.size());
//            }
//            return (T) buildQry().getSingleResult();
        } catch (Exception e) {
            String msg = "Error finding loading single result of class " + t.getName() + " - " + e.toString();
            LOGGER.log(Level.SEVERE, msg);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getSingleResult2(Class<T> t) {
        try {
            List<T> list = buildQry().getResultList();

            if (list != null && list.size() == 1) {
                return list.get(0);
            }
        } catch (Exception e) {
            String msg = "Error finding loading single result of class " + t.getName() + " - " + e.toString();
            LOGGER.log(Level.SEVERE, msg);
        }

        return null;
    }

    public static QryBuilder get(EntityManager em, Class rootClass) {
        QryBuilder qryBuilder = new QryBuilder(em, rootClass);

        return qryBuilder;
    }

    public static void main(String[] args) {
        String param = "dd(dsd)";
        String regrex = "\\([a-zA-Z ]+\\)";

        boolean matches = param.matches(regrex);

        System.out.println(matches);
    }

    /**
     *
     */
    public List findAllOrderAsc(Class t, String orderField) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e "
                    + "ORDER BY e." + orderField;
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List loadDataByField(String searchField, Object fieldValue, Class c) {
        try {
            String qry = "SELECT e FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + searchField + " =:fieldValue ";
            return em.createQuery(qry)
                    .setParameter("fieldValue", fieldValue).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List loadOrderdDataByField(String searchField, Object fieldValue, Class c, String orderField) {
        try {
            String qry = "SELECT e FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + searchField + " =:fieldValue "
                    + "ORDER BY e." + orderField;
            return em.createQuery(qry)
                    .setParameter("fieldValue", fieldValue).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public <T> T find(Class<T> t, String field, Object fieldValue) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e "
                    + "WHERE e." + field + " =:fieldValue ";
            return (T) em.createQuery(qry)
                    .setParameter("fieldValue", fieldValue).getSingleResult();
        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Unable to find " + t + " with "+field+" = " + fieldValue, e);
        }

        return null;
    }

}
