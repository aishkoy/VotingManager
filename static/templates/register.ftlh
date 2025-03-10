<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
    <title>Registration</title>
</head>
<body class="flex items-center justify-center min-h-screen bg-black">
<div class="w-full max-w-md p-8">
    <div class="relative bg-zinc-900 p-8 rounded border border-emerald-400 shadow-lg shadow-emerald-400/20">
        <div class="absolute -top-2 -left-2 w-4 h-4 bg-emerald-400 rounded-full blur-sm"></div>
        <div class="absolute -bottom-2 -right-2 w-4 h-4 bg-emerald-400 rounded-full blur-sm"></div>

        <h2 class="text-emerald-400 text-3xl font-bold mb-8 text-center">Регистрация</h2>

        <#include "error-success.ftlh">

        <form action="/register" method="POST" class="space-y-5">
            <div>
                <label for="name" class="block text-emerald-300 text-sm mb-1">ФИО:</label>
                <input type="text" id="name" name="name" required
                       class="w-full bg-zinc-800 text-white border-0 border-b-2 border-emerald-500 p-2 focus:outline-none focus:border-emerald-300 transition-colors">
            </div>
            <div>
                <label for="email" class="block text-emerald-300 text-sm mb-1">Email:</label>
                <input type="email" id="email" name="email" required
                       class="w-full bg-zinc-800 text-white border-0 border-b-2 border-emerald-500 p-2 focus:outline-none focus:border-emerald-300 transition-colors">
            </div>
            <div>
                <label for="password" class="block text-emerald-300 text-sm mb-1">Пароль:</label>
                <input type="password" id="password" name="password" required
                       class="w-full bg-zinc-800 text-white border-0 border-b-2 border-emerald-500 p-2 focus:outline-none focus:border-emerald-300 transition-colors">
            </div>

            <button type="submit"
                    class="w-full mt-6 bg-transparent border border-emerald-400 text-emerald-400 p-2 hover:bg-emerald-400 hover:text-black transition-all duration-300 font-medium">
                Зарегистрироваться
            </button>
        </form>

        <div class="mt-8 pt-4 border-t border-zinc-700 text-center">
            <p class="text-zinc-400 mb-2">Уже есть аккаунт?</p>
            <a href="/login" class="text-emerald-400 hover:text-emerald-300 transition-colors">Войти</a>
        </div>
    </div>
</div>
</body>
</html>