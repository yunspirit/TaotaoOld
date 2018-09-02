<#--同ER表达式-->
<#--${hello}-->
<html>
<head>
    <title>${title!"这里是空"}</title>
</head>
<body>
    <label>学号：</label>${student.id}<br>
    <label>姓名：</label>${student.name}<br>
    <label>住址：</label>${student.address}<br>
    学生列表：
     <table border="1">
         <#list students as s>
             <#if s_index%2==0>
                 <tr style="color:red"></tr>
             <#else >
                 <tr>
             </#if>
                 <td>${s.id}</td>
                 <td>${s.name}</td>
                 <td>${s.address}</td>
             </tr>
         </#list>
     </table>
     当前日期:${curDate?time}

</body>
</html>