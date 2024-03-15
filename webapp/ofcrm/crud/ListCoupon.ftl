<table class="table table-bordered table-striped table-hover">
    <thead>
        <tr>
          <th>${uiLabelMap.CouponId}</th>
          <th>${uiLabelMap.CouponDescription}</th>
          <th>${uiLabelMap.CouponDiscountPercentage}</th>
          <th>${uiLabelMap.CouponExpiryDate}</th>
        </tr>
    </thead>
    <tbody>
        <#list couponList as coupon>
            <tr>
              <td>${coupon.couponId?default("NA")}</td>
              <td>${coupon.description?default("NA")}</td>
              <td>${coupon.discountPercentage!}</td>
              <td>${coupon.expiryDate!}</td>

            </tr>
        </#list>
    </tbody>
</table>