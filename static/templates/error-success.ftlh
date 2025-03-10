<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Error</title>
</head>
<body>
<#if error??>
    <div class="mt-4 mb-2 p-1 text-red-700 text-center rounded-lg">
        ${error}
    </div>
</#if>
<#if success??>
    <div class="mt-4 mb-2 p-1 text-green-700 text-center rounded-lg">
        ${success}
    </div>
</#if>
</body>
</html>