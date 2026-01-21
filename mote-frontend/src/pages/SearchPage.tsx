import { useState } from 'react';
import api from '../services/api';
import { type Book } from '../types';

export function SearchPage() {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState<Book[]>([]);
  const [searching, setSearching] = useState(false);

  // --- NOVA FUNÇÃO: Adicionar à Estante ---
  const addToShelf = async (book: Book) => {
    try {
      // Estamos usando o ID 1 fixo para este MVP
      await api.post('/library/1/add', book);
      alert(`📚 Sucesso! "${book.title}" foi adicionado à sua estante.`);
    } catch (error) {
      console.error("Erro ao adicionar:", error);
      alert("⚠️ Erro: Este livro já está na estante ou houve uma falha.");
    }
  };
  // ----------------------------------------

  const handleSearch = async () => {
    if (!query.trim()) return;
    setSearching(true);
    try {
      const response = await api.get(`/books/search?q=${query}`);
      setResults(response.data);
    } catch (error) {
      console.error("Erro na busca:", error);
      alert("Erro ao buscar livros.");
    } finally {
      setSearching(false);
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Buscar Novos Livros</h2>
      
      <div style={{ marginBottom: '20px', display: 'flex', gap: '10px' }}>
        <input 
          type="text" 
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Digite o nome do livro (ex: Clean Code)..."
          style={{ padding: '10px', flex: 1, borderRadius: '4px', border: '1px solid #ccc' }}
          onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
        />
        <button 
          onClick={handleSearch} 
          disabled={searching}
          style={{ padding: '10px 20px', cursor: 'pointer', background: '#3498db', color: 'white', border: 'none', borderRadius: '4px' }}
        >
          {searching ? 'Buscando...' : 'Buscar'}
        </button>
      </div>

      <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap' }}>
        {results.map((book) => (
          <div key={book.isbn} style={{ width: '150px', textAlign: 'center', border: '1px solid #eee', padding: '10px', borderRadius: '8px' }}>
            <img 
              src={book.coverUrl || 'https://via.placeholder.com/128x190?text=Sem+Capa'} 
              alt={book.title}
              style={{ width: '100%', borderRadius: '4px', height: '200px', objectFit: 'cover' }} 
            />
            <h4 style={{ fontSize: '0.9rem', margin: '10px 0 5px', height: '40px', overflow: 'hidden' }}>{book.title}</h4>
            
            {/* Botão Agora Funcional */}
            <button 
              onClick={() => addToShelf(book)}
              style={{ 
                fontSize: '0.8rem', 
                padding: '5px 10px', 
                width: '100%', 
                background: '#27ae60', 
                color: 'white', 
                border: 'none', 
                borderRadius: '4px',
                cursor: 'pointer' 
              }}
            >
              + Adicionar
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}