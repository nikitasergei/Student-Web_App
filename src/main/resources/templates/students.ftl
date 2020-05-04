<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    <#else>
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

                <#list page.content as student>
                    <tr>
                        <#if student.id??>
                            <td align="center">${student.id}</td></#if>
                        <#if student.username??>
                            <td align="center">${student.username}</td></#if>
                        <#if student.email??>
                            <td align="center">${student.email}</td></#if>
                        <#if student.id??>
                            <td align="center">
                            <button tupe="button" class="btn btn-primary">
                                <a style="color: #ffffff" href="/studentCourses/${student.id}">Show</a>
                            </button></td></#if>
                    </tr>
                <#else>
                    <p style="color: #c80201">
                        Student list is empty!
                    </p>
                </#list>
            </table>
            <@p.pager url page/>
        </div>
    </#if>
</@pt.page>