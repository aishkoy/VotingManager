<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Who's winning?</title>
    <link rel="stylesheet" href="css/candidates.css">
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
</head>

<body>
<div class="container flex flex-col align-center">
    <h1>Who's winning?</h1>
    <main class="flex flex-wrap align-center">
        <#list candidates as candidate>
            <div class="card ${((candidate?index == 0) && (candidate.percentage != 0.0))?then('bg-red-500 border-red-600 border-2', '')}">
                <div class="flex flex-col align-center">
                    <img src="${candidate.photo}" class="w-50 h-50 rounded-md" alt="${candidate.name}">
                    <p>${candidate.name} has ${candidate.percentage?string("0.##")}% of all votes</p>
                </div>
            </div>
        </#list>
        <a class="back flex align-center" href="/">back to main</a>
        <a class="back flex align-center" href="/logout">Logout</a>
    </main>
</div>
</body>

</html>