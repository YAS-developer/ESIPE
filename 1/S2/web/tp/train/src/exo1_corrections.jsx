import * as React from 'react';
import * as ReactDOM from 'react-dom/client';

const data = [
  { id: "1", title: "Barbie", company: "Warner Bros.", gross: "$1,445,638,421" },
  { id: "2", title: "The Super Mario Bros. Movie", company: "Universal", gross: "$1,361,992,475" }
];

function MovieItem({ title }) {
  return <li>{title}</li>;
}

function MovieList() {
  const [movies, setMovies] = React.useState([]);

  React.useEffect(() => {
    setMovies(data);
  }, []);

  return (
    <ul>
      {movies.map(movie => (
        <MovieItem key={movie.id} title={movie.title} />
      ))}
    </ul>
  );
}

async function fetchMovies(setMovies) {
  try {
    const response = await fetch('/api/movie');
    const movies = await response.json();
    setMovies(movies);
  } catch (error) {
    console.error('Error fetching movies:', error);
  }
}

async function deleteMovie(id, setMovies) {
  try {
    await fetch(`/api/movie/${id}`, { method: 'DELETE' });
    fetchMovies(setMovies);
  } catch (error) {
    console.error('Error deleting movie:', error);
  }
}

async function addMovie(movie, setMovies) {
  try {
    await fetch('/api/movie', {
      method: 'POST',
      body: JSON.stringify(movie),
    });
    fetchMovies(setMovies);
  } catch (error) {
    console.error('Error adding movie:', error);
  }
}

function MovieDelete({ id, setMovies }) {
  return <button onClick={() => deleteMovie(id, setMovies)}>X</button>;
}

function Filter({ setFilter }) {
  return <input type="text" onChange={(e) => setFilter(e.target.value)} />;
}

function MovieForm({ setMovies }) {
  const [title, setTitle] = React.useState('');
  const [company, setCompany] = React.useState('');
  const [gross, setGross] = React.useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const newMovie = { title, company, gross };
    addMovie(newMovie, setMovies);
    setTitle('');
    setCompany('');
    setGross('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Company"
        value={company}
        onChange={(e) => setCompany(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Gross"
        value={gross}
        onChange={(e) => setGross(e.target.value)}
        required
      />
      <button type="submit">Add Movie</button>
    </form>
  );
}

function App() {
  const [filter, setFilter] = React.useState('');
  const [movies, setMovies] = React.useState([]);

  React.useEffect(() => {
    fetchMovies(setMovies);
  }, []);

  const filteredMovies = movies.filter(movie =>
    movie.title.toLowerCase().startsWith(filter.toLowerCase())
  );

  return (
    <>
      <h1>Movie Database</h1>
      <Filter setFilter={setFilter} />
      <MovieForm setMovies={setMovies} />
      <ul>
        {filteredMovies.map(movie => (
          <li key={movie.id}>
            {movie.title} <MovieDelete id={movie.id} setMovies={setMovies} />
          </li>
        ))}
      </ul>
    </>
  );
}

window.onload = () => {
  let appDOM = document.getElementById("App");

  let root = ReactDOM.createRoot(appDOM);
  root.render(<App />);
};
