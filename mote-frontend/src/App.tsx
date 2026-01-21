import { useEffect, useState } from 'react';
import api from './services/api';
import { type LibraryEntry } from './types';

function App() {
  // Estado para guardar os livros da estante
  const [shelf, setShelf] = useState<LibraryEntry[]>([]);
  const [loading, setLoading] = useState(true);

  // useEffect roda assim que a tela abre
  useEffect(() => {
    // Busca a estante do Usuário 1
    api.get('/library/1')
      .then(response => {
        setShelf(response.data); // Guarda os dados no estado
        setLoading(false);
      })
      .catch(error => {
        console.error("Erro ao buscar estante:", error);
        setLoading(false);
      });
  }, []);

  return (
    <div style={{ padding: '20px' }}>
      <h1>Minha Estante Mote</h1>
      
      {loading ? (
        <p>Carregando...</p>
      ) : (
        <div style={{ display: 'flex', gap: '20px', marginTop: '20px' }}>
          {shelf.length === 0 ? (
            <p>Nenhum livro na estante ainda.</p>
          ) : (
            shelf.map(entry => (
              <div key={entry.id} style={{ border: '1px solid #ccc', padding: '10px', borderRadius: '8px', width: '200px' }}>
                {entry.book.coverUrl && (
                  <img src={entry.book.coverUrl} alt={entry.book.title} style={{ width: '100%', borderRadius: '4px' }} />
                )}
                <h3>{entry.book.title}</h3>
                <p style={{ color: '#666' }}>{entry.book.author}</p>
                <small>Adicionado em: {new Date(entry.addedAt).toLocaleDateString()}</small>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  )
}

export default App;