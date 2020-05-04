<#import "parts/pageTemplate.ftl" as pt>

<@pt.page>
    <div class="container mt-5" style="width: 50%">
        <div class="card mb-3" style="max-width: 540px;">
            <div class="row no-gutters">
                <div class="col-md-4" style="display: flex; justify-content: center">
                    <#if file??>
                        <img src="/img/${file}" class="card-img" style="height: 320px;" alt="user photo"
                             id="pic">
                    <#else>
                        <img src="/img/no_photo_user_profile.jpg" class="card-img-top" alt="empty avatar" id="pic">
                    </#if>
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <h2 style="color: red">${user.username}</h2>
                        <form method="post" enctype="multipart/form-data">
                            <div class="form-group row">
                                <label class="col-sm col-form-label" style="color: red">Password:</label>
                                <div class="col-sm">
                                    <input type="password" name="password" class="form-control" placeholder="Password"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm col-form-label" style="color: red">Email:</label>
                                <div class="col-sm">
                                    <input type="email" name="email" class="form-control" placeholder="some@some.com"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm col-form-label" style="color: red">Image:</label>
                                <div class="col-sm">
                                    <input type="file" name="file" style="border-radius: 5px"/>
                                </div>
                            </div>

                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary" type="submit">Save</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <#if role=="student">
            <a href="/${user.id}/addStudentAtCourse" class="btn btn-primary stretched-link">Enroll in a course</a>
        </#if>
        <a href="/${id}/my-archives/" class="btn btn-primary stretched-link">Show my archives notes</a>
    </div>
</@pt.page>