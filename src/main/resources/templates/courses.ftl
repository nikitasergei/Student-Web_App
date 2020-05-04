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
                    <th scope="col">Course Name</th>
                    <th scope="col">Teacher</th>
                    <th scope="col">Course's Archive</th>
                    <#if isTeacher??>
                        <th scope="col">Edit course</th>
                    </#if>
                </tr>
                <#list page.content as course>
                    <tr>
                        <#if course.id??>
                            <td align="center">${course.id}</td></#if>
                        <#if course.courseName??>
                            <td align="center">${course.courseName}</td></#if>
                        <#if course.teacher??>
                            <td align="center">${course.teacher.username}</td></#if>
                        <#if course.id??>
                            <td align="center">
                                <button tupe="button" class="btn btn-primary">
                                    <a style="color: #ffffff" href="/archive/${course.id}">Show</a>
                                </button>
                            </td>
                            <#if isTeacher??>
                                <td align="center">
                                    <button tupe="button" class="btn btn-primary">
                                        <a style="color: #ffffff" href="/editCourse/${course.id}">Edit</a>
                                    </button>
                                </td>
                            </#if>
                        </#if>
                    </tr>
                <#else>
                    <p style="color: #c80201">
                        Course list is empty!
                    </p>
                </#list>
            </table>
            <@p.pager url page/>
            <#if isTeacher??>
                <a href="/course/add">
                    <button type="button" class="btn btn-primary">Add one more course</button>
                </a>
            </#if>
        </div>
    </#if>
</@pt.page>
