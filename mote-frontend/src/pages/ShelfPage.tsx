import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'; // <--- Importante
import api from '../services/api';
import { type LibraryEntry } from '../types';

export function ShelfPage() {
  const [shelf, setShelf] = useState<LibraryEntry[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get('/library/1')
      .then(response => {
        setShelf(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error("Erro:", error);
        setLoading(false);
      });
  }, []);

  return (
    <div style={{ padding: '20px' }}>
      <h2>Minha Estante</h2>
      {loading ? <p>Carregando...</p> : (
        <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap' }}>
          {shelf.length === 0 ? <p>Estante vazia.</p> : shelf.map(entry => (
            // Transformamos o card inteiro em um Link
            // Usamos entry.book.id porque o MoteController pede o ID do livro
            <Link key={entry.id} to={`/book/${entry.book.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
              <div style={{ width: '150px', textAlign: 'center', cursor: 'pointer', transition: 'transform 0.2s' }}>
                <img 
                  src={entry.book.coverUrl || 'https://via.placeholder.com/128x190?text=Sem+Capa'} 
                  alt={entry.book.title}
                  style={{ width: '100%', borderRadius: '8px', boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }} 
                />
                <h4 style={{ margin: '10px 0 5px', fontSize: '1rem' }}>{entry.book.title}</h4>
                <p style={{ margin: 0, fontSize: '0.8rem', color: '#666' }}>{entry.book.author}</p>
              </div>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}