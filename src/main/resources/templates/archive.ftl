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
                    <th scope="col">Note's Id</th>
                    <th scope="col">Course</th>
                    <th scope="col">Teacher</th>
                    <th scope="col">Student</th>
                    <th scope="col">Rating</th>
                </tr>
                <#list page.content as archive>
                <tr>
                    <#if archive.id??>
                        <td align="center">${archive.id}</td></#if>
                    <#if archive.course??>
                        <td align="center">${archive.course.courseName}</td></#if>
                    <#if archive.teacher??>
                        <td align="center">${archive.teacher.username}</td></#if>
                    <#if archive.student??>
                        <td align="center">${archive.student.username}</td></#if>
                    <#if archive.rating??>
                        <td align="center">${archive.rating}</td></#if>
                    <#else>
                        <p style="color: #c80201">
                            Archive list is empty!
                        </p>
                    </#list>
            </table>
            <@p.pager url page/>
            <#if isTeacher??>
                <a href="/editArchive/${course.id}">
                    <button type="button" class="btn btn-primary">Add one more archive note</button>
                </a>
            </#if>
        </div>
    </#if>
</@pt.page>