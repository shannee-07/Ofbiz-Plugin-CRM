//context.orders = select("orderId", "entryDate", "statusId", "createdBy", "currencyUom", "grandTotal", "originFacilityId").from("OrderHeader").queryList();


import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import java.util.List;
import java.util.LinkedList;
import org.apache.ofbiz.base.util.UtilMisc;

minAmount=0;
if(context.minAmount == null){
    minAmount = context.minAmount;
}
// Get all the parameters that are passed
def orderId = context.orderId
minAmount = context.minAmount?:0;
def statusId = context.statusId
def sortBy = context.sortBy


//System.out.println("VALUE OF MINAMOUNT "+context.minAmount);

try {

// Construct the base condition for the query

// Check if orderId is provided
    if (orderId) {
        context.orders = select("orderId", "entryDate", "statusId", "createdBy", "currencyUom", "grandTotal", "originFacilityId").from("OrderHeader").where("orderId",orderId).queryList();

    } else {
        List<EntityCondition> conditionList=  new LinkedList<EntityCondition>();
        conditionList.add(EntityCondition.makeCondition("grandTotal", EntityOperator.GREATER_THAN, BigDecimal.valueOf(minAmount as double)));

        // Apply condition for statusId if provided
        if (statusId && statusId!="All") {
            conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, statusId))
        }

        if (sortBy) {

            if ((String) sortBy == "entryDate") {
                conditionList.add(EntityCondition.makeCondition("entryDate", EntityOperator.NOT_EQUAL, null))
                context.orders = select("orderId", "entryDate", "statusId", "createdBy", "currencyUom", "grandTotal", "originFacilityId").from("OrderHeader").where(conditionList).orderBy("-entryDate").queryList();

            } else {
                conditionList.add(EntityCondition.makeCondition("grandTotal", EntityOperator.NOT_EQUAL, null));
                context.orders = select("orderId", "entryDate", "statusId", "createdBy", "currencyUom", "grandTotal", "originFacilityId").from("OrderHeader").where(conditionList).orderBy("-grandTotal").queryList();
            }
        } else {
            System.out.println("conditionlist: "+conditionList);
            context.orders = select("orderId", "entryDate", "statusId", "createdBy", "currencyUom", "grandTotal", "originFacilityId").from("OrderHeader").where(conditionList).queryList();
        }
    }

} catch (Exception e) {
    context.orders = e;
}

