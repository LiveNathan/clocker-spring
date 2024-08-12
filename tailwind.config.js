/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/main/resources/templates/**/*.html'],
  theme: {
    extend: {},
  },
  plugins: [require('@tailwindcss/container-queries'),require('daisyui')],
  daisyui: {
    themes: ["light", "dark", "forest"],
  },
}

