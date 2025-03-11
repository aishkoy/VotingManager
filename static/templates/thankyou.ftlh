<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Thank you!!</title>
    <link rel="stylesheet" href="css/candidates.css">
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
</head>

<body>
<div class="container flex flex-col align-center">
    <h1>Thank you for your vote!</h1>
    <#include "error-success.ftlh">
    <main class="flex flex-wrap align-center">
        <div class="card">
            <div class="flex flex-col align-center" href="#">
                <img src="${candidate.photo}" class="w-50 h-50 rounded-md" alt="${candidate.name}">
                <p>Thank you for your vote!</p>
                <p>Now I have ${candidate.percentage?string("0.##")}% of all the votes.</p>
            </div>
        </div>
        <a class="back flex align-center" href="/">back to main</a>
    </main>
</div>
</body>

</html>