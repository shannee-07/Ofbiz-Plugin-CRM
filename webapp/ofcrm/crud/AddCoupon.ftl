<form method="post" action="<@ofbizUrl>createCouponEvent</@ofbizUrl>" name="createCouponEvent" class="form-horizontal">

  <div class="control-group">
      <label class="control-label" for="couponTypeId">${uiLabelMap.CouponType}</label>
      <div class="controls">
        <select id="couponTypeId" name="couponTypeId">
          <#list couponTypes as type>
            <option value='${type.couponTypeId}'>${type.couponTypeId}: ${type.description}</option>
          </#list>
        </select>
      </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="description">${uiLabelMap.CouponDescription}</label>
    <div class="controls">
      <input type="text" id="description" name="description" required>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="discountPercentage">${uiLabelMap.CouponDiscountPercentage}</label>
    <div class="controls">
      <input type="text" id="discountPercentage" name="discountPercentage" required>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="expiryDate">${uiLabelMap.CouponExpiryDate}</label>
    <div class="controls">
      <input type="date" id="expiryDate" name="expiryDate">
    </div>
  </div>
  <div class="control-group">
      <label class="control-label" for="productCategoryId">${uiLabelMap.ProductCategoryId}</label>
      <div class="controls">
        <input type="text" id="productCategoryId" name="productCategoryId">
      </div>
  </div>

  <div class="control-group">
        <label class="control-label" for="productId">${uiLabelMap.ProductId}</label>
        <div class="controls">
          <input type="text" id="productId" name="productId">
        </div>
  </div>


  <div class="control-group">
        <label class="control-label" for="upto">${uiLabelMap.UpTo}</label>
        <div class="controls">
          <input type="number" id="upto" name="upto">
        </div>
  </div>

  <div class="control-group">
          <label class="control-label" for="minPurchase">${uiLabelMap.MinPurchase}</label>
          <div class="controls">
            <input type="text" id="minPurchase" name="minPurchase">
          </div>
    </div>

  <div class="control-group">
    <div class="controls">
      <button type="submit" class="btn">${uiLabelMap.CommonAdd}</button>
    </div>
  </div>
</form>