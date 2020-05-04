<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <div class="container mt-5" style="width: 50%">
        <h3> Edit owner </h3>
        <#if savingReport??>${savingReport}</#if>
        <div class="form-group mt-3">
            <form method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="<#if owner?? && owner.id??>${owner.id}</#if>">
                <div class="form-group">
                    <input type="text" name="name"
                           class="form-control ${(nameError??)?string('is-invalid', '')}"
                           value="<#if owner?? && owner.name??>${owner.name}</#if>"
                           placeholder="Enter owner's name">
                    <div class="invalid-feedback">
                        <#if nameError??>${nameError}</#if>
                    </div>
                </div>
                <div class="form-group">
                    <input type="text" name="address" class="form-control ${(addressError??)?string('is-invalid', '')}"
                           value="<#if owner?? && owner.address??>${owner.address}</#if>"
                           placeholder="Enter the address">
                    <div class="invalid-feedback">
                        <#if addressError??>${addressError}</#if>
                    </div>
                </div>
                <div class="form-group mt-2">
                    <input type="submit" class="btn btn-primary" value="Submit">
                </div>
            </form>
        </div>
    </div>
</@pt.page>