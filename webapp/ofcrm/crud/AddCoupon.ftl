<form method="post" action="<@ofbizUrl>createCouponFtl</@ofbizUrl>" name="createCouponEvent" class="form-horizontal">

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
      <input type="text" id="expiryDate" name="expiryDate">
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button type="submit" class="btn">${uiLabelMap.CommonAdd}</button>
    </div>
  </div>
</form>