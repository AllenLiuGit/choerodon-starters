package io.choerodon.mybatis.pagehelper.parser

import net.sf.jsqlparser.expression.Expression
import net.sf.jsqlparser.schema.Column
import net.sf.jsqlparser.schema.Table
import net.sf.jsqlparser.statement.select.OrderByElement
import net.sf.jsqlparser.statement.select.PlainSelect
import net.sf.jsqlparser.statement.select.SelectBody
import net.sf.jsqlparser.statement.select.SelectExpressionItem
import net.sf.jsqlparser.statement.select.SetOperationList
import org.apache.ibatis.mapping.MappedStatement
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import sun.text.resources.is.CollationData_is

/**
 * Created by superlee on 2018/10/18.
 */
@PrepareForTest(Column.class)
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Sputnik.class)
class SqlServerParserSepc extends Specification {

    SqlServerParser serverParser = new SqlServerParser()


    def "WrapSetOperationList"() {
        given:
        SetOperationList setOperationList = Mock(SetOperationList)
        List<SelectBody> selects = Mock(List)
        setOperationList.getSelects() >> selects
        PlainSelect plainSelect = Mock(PlainSelect)
        selects.get(_) >> plainSelect

        SelectExpressionItem selectExpressionItem = Mock(SelectExpressionItem)
        plainSelect.getSelectItems() >> [selectExpressionItem]

        Column column = PowerMockito.mock(Column.class)
        column.getTable() >> Mock(Table)
        column.getColumnName()>>"name"

        when:
        serverParser.wrapSetOperationList(setOperationList)
        then:
        2*selectExpressionItem.getExpression() >>column

    }

    def "GetPageSelect"() {
    }

    def "GetOrderByElements"() {
    }

    def "ProcessSelectBody"() {}

    def "CloneOrderByElement"() {
        given:
        OrderByElement orderByElement = Mock(OrderByElement)
//        Expression expression = Mock(Expression)
        orderByElement.isAsc()>>true
        orderByElement.isAscDescPresent()>>true

        when:
        def value = serverParser.cloneOrderByElement(orderByElement, "column")
        then:
        value.getNullOrdering() == null
    }
}
