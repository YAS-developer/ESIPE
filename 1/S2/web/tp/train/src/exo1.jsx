import * as React from 'react';
import * as ReactDOM from 'react-dom/client';

// Définir des données de film
let data = [
  { "id": "1", "title": "Barbieee", "company": "Warner Bros.", "gross": "$1,445,638,421" },
  { "id": "2", "title": "The Super Mario Bros. Movie", "company": "Universal", "gross": "$1,361,992,475" }
];

// Composant MovieItem qui reçoit un film en tant que prop
function MovieItem({ movie }) {
  return <li>{movie.title} - {movie.company} - {movie.gross}</li>;
}

// Composant MovieList qui reçoit un tableau de films en tant que prop et le transmet à MovieItem
function MovieList({ movies }) {
  return (
    <ul>
      {movies.map(movie => (
        <MovieItem key={movie.id} movie={movie} />
      ))}
    </ul>
  );
}

// Fonction qui change la couleur de fond de l'élément avec l'ID App
function taio() {
  document.getElementById('App').style.backgroundColor = 'blue';
}

// Composant App qui passe les données de films à MovieList
function App() {
  return (
    <>
      <h1>Movie Database</h1>
      <MovieList movies={data} />
      <button onClick={taio}>HELLO</button>
    </>
  );
}

// Charger l'application lorsque la fenêtre est prête
window.onload = () => {
  let appDOM = document.getElementById("App");

  let root = ReactDOM.createRoot(appDOM);
  root.render(<App />);
};

