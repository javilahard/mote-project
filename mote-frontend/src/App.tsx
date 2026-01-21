import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { ShelfPage } from './pages/ShelfPage';
import { SearchPage } from './pages/SearchPage';
import { BookDetailsPage } from './pages/BookDetailsPage'; // <--- Import novo

function App() {
  return (
    <BrowserRouter>
      <nav style={{ background: '#fff', padding: '15px 20px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)', display: 'flex', gap: '20px' }}>
        <h1 style={{ margin: 0, fontSize: '1.2rem', color: '#2c3e50' }}>Mote</h1>
        <Link to="/" style={{ textDecoration: 'none', color: '#3498db', fontWeight: 'bold' }}>Minha Estante</Link>
        <Link to="/search" style={{ textDecoration: 'none', color: '#3498db', fontWeight: 'bold' }}>Buscar Livros</Link>
      </nav>

      <div style={{ maxWidth: '1000px', margin: '0 auto', paddingTop: '20px' }}>
        <Routes>
          <Route path="/" element={<ShelfPage />} />
          <Route path="/search" element={<SearchPage />} />
          {/* Rota dinâmica: :id vai pegar o ID do livro */}
          <Route path="/book/:id" element={<BookDetailsPage />} /> 
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;