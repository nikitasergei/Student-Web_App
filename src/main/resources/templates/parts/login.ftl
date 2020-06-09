<#include "security.ftl">
<#macro login path isRegisterForm>
    <form action="${path}" method="post" xmlns="http://www.w3.org/1999/html">
        <div class="form-group row ml-5">
            <#if isRegisterForm>
                <div class="container m-5 ">
                    <div class="col-3">
                        <div class="list-group list-group" id="list-tab">
                            <a class="btn btn-outline-info active" id="list-home-list"
                               data-toggle="list" role="tab" value="teacher" style="color: white">Teacher</a>
                            <br>
                            <a class="btn btn-outline-info" id="list-profile-list"
                               data-toggle="list" role="tab"
                               value="student" style="color: white">Student</a>
                        </div>
                    </div>
                </div>
            </#if>

            <label class="col-sm-2 col-form-label" style="color: #c80201; font-weight: bold"> User: </label>
            <div class="col-sm-6">
                <input type="text" name="username"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}" placeholder="User name"
                       value="<#if user??>${user.username}</#if>"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row ml-5">
            <label class="col-sm-2 col-form-label" style="color: #c80201; font-weight: bold"> Password: </label>
            <div class="col-sm-6">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Password"
                       value=""/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row ml-5">
                <label class="col-sm-2 col-form-label" style="color: #c80201; font-weight: bold"> Repeat
                    password: </label>
                <div class="col-sm-6">
                    <input type="password" name="passwordConfirm"
                           class="form-control ${(passwordConfirmError??)?string('is-invalid', '')}"
                           placeholder="Repeat password"
                           value=""/>
                    <#if passwordConfirmError??>
                        <div class="invalid-feedback">
                            ${passwordConfirmError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row ml-5">
                <label class="col-sm-2 col-form-label" style="color: #c80201; font-weight: bold"> Email: </label>
                <div class="col-sm-6">
                    <input type="email" name="email"
                           class="form-control ${(emailError??)?string('is-invalid', '')}" placeholder="some@some.com"
                           value=""/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-sm-4 mb-2 ml-5">
                <div class="g-recaptcha" data-sitekey="6LcWWbcUAAAAACalmOS_jK4jFzZIk0eUXUQz1JqC"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
            <input type="hidden" name="userRole" value=""/>
            <script type="text/javascript">
                function getRole() {
                    let role = document.getElementsByClassName("active")[0].getAttribute("value");
                    let inputElement = document.querySelector('[name = "userRole"]');
                    inputElement.setAttribute("value", role);
                }
            </script>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <a href="/registration" class="btn btn-primary stretched-link btn-lg">Sign up</a>
        </#if>
        <button <#if isRegisterForm>onclick="getRole()"</#if> class="btn btn-primary btn-lg" type="submit">
            <#if !isRegisterForm>
                Sign in
            <#else>
                Save
            </#if>
        </button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">
            <#if user??>Sign out<#else>Sign in</#if>
        </button>
    </form>
</#macro>