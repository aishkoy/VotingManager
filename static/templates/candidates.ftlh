<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Vote for us!</title>
  <link rel="stylesheet" href="../css/candidates.css">
</head>

<body>
  <div class="container flex flex-col align-center">
    <h1>Vote for us</h1>
    <main class="flex flex-wrap align-evenly">
      <#list candidates as candidate>
        <div class="card">
          <form action="/vote" method="post" class="flex flex-col align-center" href="/candidate?candidateId=${candidate.id}">
            <img src="${candidate.photo}" class="w-50 h-50 rounded-md" alt="${candidate.name}">
            <p>${candidate.name}</p>
            <input name="candidateId" type="hidden" value="${candidate.id}">
            <p>Голосов: ${candidate.votes}</p>
            <p>Процент: ${candidate.percentage?string("0.##")}%</p>
            <button type="submit" class="vote flex align-center">Vote</button>
          </form>
        </div>
      </#list>
    </main>

    <p>-------------------------------------------------------</p>
    <a class="back flex align-center" href="/votes">Show total votes</a>
  </div>
</body>

</html>