<#include "security.ftl">
<#import "login.ftl" as l>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS6Lha_bj_4Z4Bh-jmzFYkd7mzWNsjjk6kWSl1Fz5D6c_B9D3dO&usqp=CAU"
             class="rounded"
             width="50" height="50" alt="logo">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item">
                <a class="nav-link" href="/startPage">Start Page</a>
            </li>
            <#if !known>
                <li class="nav-item">
                    <a class="nav-link" href="/registration">Registration</a>
                </li>
            </#if>
            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/courses">Courses list</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/students">Student list</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/teachers">Teacher list</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/profile/${currentUserId}">Profile</a>
                </li>
            </#if>
        </ul>
        <div class="navbar-text mr-3"><#if user??>${name}<#else>Please, login</#if></div>
        <@l.logout />
    </div>
</nav>