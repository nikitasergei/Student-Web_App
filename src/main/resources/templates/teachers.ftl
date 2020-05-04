<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <#import "parts/pager.ftl" as p>

    <div class="pl-5 pr-5 pt-2">
        <@p.pager url page/>
        <table class="table table-light">
            <thead class="thead-light">
            <tr class="table-active" align="center">
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Email</th>
                <th scope="col">List of courses</th>
            </tr>
            <#list page.content as teacher>
                <tr>
                    <#if teacher.id??>
                        <td align="center">${teacher.id}</td></#if>
                    <#if teacher.username??>
                        <td align="center">${teacher.username}</td></#if>
                    <#if teacher.email??>
                        <td align="center">${teacher.email}</td></#if>
                    <#if teacher.id??>
                        <td align="center"><a href="/teacherCourses/${teacher.id}">
                                <button type="button" class="btn btn-primary">Show</button>
                            </a></td>
                    </#if>
                </tr>
            <#else>
                <p style="color: #c80201">
                    Teacher list is empty!
                </p>
            </#list>
        </table>
        <@p.pager url page/>
        <#--        <#if isAdmin>-->
        <#--                    <a href="/editTeacher">-->
        <#--                        <button type="button" class="btn btn-primary">Add teacher</button>-->
        <#--                    </a>-->
        <#--        </#if>-->
    </div>
</@pt.page>