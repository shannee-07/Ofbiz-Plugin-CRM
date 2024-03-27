<html>
  <head>
    <title>${layoutSettings.companyName}</title>
    <meta name="viewport" content="width=device-width, user-scalable=no"/>
    <#if webSiteFaviconContent?has_content>
      <link rel="shortcut icon" href="">
    </#if>
    <#list layoutSettings.styleSheets as styleSheet>
      <link rel="stylesheet" href="${StringUtil.wrapString(styleSheet)}" type="text/css"/>
    </#list>

    <style>
    </style>
  </head>
<body>
    <div class="container">
        <center><h1>Orders Information</h1></center>


<form id="filterForm" action="<@ofbizUrl>ListOrdersFtl</@ofbizUrl>" method="get">
    <!-- Row for inputs: Order ID, Minimum Amount, and Order Status -->
    <div class="filter-row">
        <label for="orderId">Order ID:</label>
        <input type="text" id="orderId" name="orderId" placeholder="Enter order ID">  

        <label for="minAmount">Minimum Amount:</label>
        <input type="number" id="minAmount" name="minAmount" placeholder="Enter minimum amount">            

        <label for="orderStatus">Order Status:</label>
        <select id="orderStatus" name="statusId">
            <option value="All">All</option>
            <option value="ORDER_APPROVED">Approved</option>
            <option value="ORDER_COMPLETED">Completed</option>
            <option value="ORDER_CANCELLED">Cancelled</option>
            <option value="ORDER_CREATED">Created</option>
            <option value="ORDER_HOLD">Hold</option>
            <option value="ORDER_PROCESSING">Processing</option>
            <option value="ORDER_REJECTED">Rejected</option>
            <option value="ORDER_SENT">Sent</option>
        </select>
    </div>

    <!-- Row for checkboxes: Sort By Entry Date and Sort By Grand Total -->
   <div class="filter-row">
    <label>Sort By:</label>
    <input type="radio" id="sortByEntryDate" name="sortBy" value="entryDate">
    <label for="sortByEntryDate">Entry Date</label>
    <input type="radio" id="sortByGrandTotal" name="sortBy" value="grandTotal">
    <label for="sortByGrandTotal">Grand Total</label>
</div>


    <!-- Submit Button -->
    <button type="submit">Filter</button>
</form>




        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Entry Date</th>
                    <th>Status</th>
                    <th>Created By</th>
                    <th>Currency</th>
                    <th>Grand Total</th>
                    <th>Origin Facility</th>
                </tr>
            </thead>
            <tbody id="ordersTableBody">
                <#list orders as order>
                    <tr>
                        <td>${order.orderId?default("NA")}</td>
                        <td>${order.entryDate?default("NA")}</td>
                        <td>${order.statusId?default("NA")}</td>
                        <td>${order.createdBy?default("NA")}</td>
                        <td>${order.currencyUom?default("NA")}</td>
                        <td>${order.grandTotal?default("NA")}</td>
                        <td>${order.originFacilityId?default("NA")}</td>
                    </tr>
                </#list>
            </tbody>



        </table>
    </div>

    <script src="script.js"></script>
</body>
</html>
