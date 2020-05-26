<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <div class="container mt-5" style="width: 50%">
        <h3 style="color: red"><#if course?? && course.id??> Edit course<#else> Add new course</#if></h3>
        <div style="color: red"><#if message??>${message}</#if>
        </div>
        <div class="form-group mt-3">
            <form method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="<#if course?? && course.id?? >${course.id}</#if>">
                <div class="form-group">
                    <label class="col-sm-2 col-form-label" style="font-size: 20px; color:blue"> Course: </label>
                    <input type="text" name="courseName"
                           class="form-control ${(courseNameError??)?string('is-invalid', '')}"
                           value="<#if course?? && course.courseName?? >${course.courseName}</#if>"
                           placeholder="Enter the course name">
                    <div class="invalid-feedback" style="font-size: 20px; color:red">
                        <#if courseNameError??>${courseNameError}</#if>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 col-form-label" style="font-size: 20px; color:blue"> Teacher: </label>
                    <select class="custom-select custom-select-lg mb-3" name="username">
                        <#list page.content as teacher>
                            <option><#if teacher??>${teacher.username}<#else>There are  no registered teachers in system</#if></option>
                        </#list>
                    </select>
<#--                    <input type="text" name="username" class="form-control ${(teacherError??)?string('is-invalid', '')}"-->
<#--                           value="<#if course?? && course.teacher??>${course.teacher.username}</#if>"-->
<#--                           placeholder="Enter the Teacher name">-->
<#--                    <div class="invalid-feedback" style="font-size: 20px; color:red">-->
<#--                        <#if teacherError??>${teacherError}</#if>-->
<#--                    </div>-->
                </div>

                <div class="form-group mt-2">
                    <input type="submit" class="btn btn-primary" value="Submit">
                </div>
            </form>
        </div>
    </div>
</@pt.page>