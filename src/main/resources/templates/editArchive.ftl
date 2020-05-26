<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <div class="container mt-5" style="width: 50%">
        <h3 style="color: red"> Add archive note </h3>
        <#if message??>${message}</#if>
        <div class="form-group mt-3">
            <form method="post">
                <input type="hidden" name="id"
                       value="<#if archive?? && archive.id??>${archive.id}</#if>">
                <label class="col-sm-2 col-form-label" style="font-size: 20px; color:blue">
                    Course: </label>
                <select class="custom-select" id="courseName" name="courseName">
                    <option>${course.courseName}</option>
                </select>
                <input type="hidden" name="course" value="">
                <label class="col-sm-2 col-form-label" style="font-size: 20px; color:blue">
                    Teacher:</label>
                <select class="custom-select" id="teacherName" name="teacherName">
                    <option>${teacher.username}</option>
                </select>
                <label class="col-sm-2 col-form-label" style="font-size: 20px; color:blue"> Student: </label>
                <select class="custom-select" id="studentName" name="studentName">
                    <#list students as student>
                        <option>${student.username}</option>
                    </#list>
                </select>
                <label class="col-sm-2 col-form-label" style="font-size: 20px; color:blue"> Rating: </label>
                <select class="custom-select" id="rating" name="rating">
                    <option selected value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                </select>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group mt-2">
                    <input class="btn btn-primary" type="submit" value="Add to archive"/>
                </div>
            </form>
        </div>
    </div>
</@pt.page>