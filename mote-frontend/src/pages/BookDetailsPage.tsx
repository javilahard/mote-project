import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';
import { type LibraryEntry } from '../types';

export function BookDetailsPage() {
  const { id } = useParams(); // Pega o ID da URL (ex: 5)
  const [entry, setEntry] = useState<LibraryEntry | null>(null);
  const [loading, setLoading] = useState(true);

  // Estados do Formulário de Mote
  const [content, setContent] = useState('');
  const [location, setLocation] = useState('');
  const [theme, setTheme] = useState('INSIGHTFUL'); // Valor padrão

  // Função para buscar os dados (reutilizável para atualizar a lista depois de criar)
  const fetchDetails = () => {
    // Como não criamos um endpoint para pegar 1 livro específico, 
    // buscamos a estante toda e filtramos (Workaround do MVP)
    api.get('/library/1').then(response => {
      const found = response.data.find((item: LibraryEntry) => item.book.id === Number(id));
      setEntry(found || null); 
      setLoading(false);  
    }); 
  }; 

  useEffect(() => {
    fetchDetails();
  }, [id]);

  const handleCreateMote = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!content || !location) return alert("Preencha texto e localização!");

    try {
      // POST /api/motes/{userId}/{bookId}
      await api.post(`/motes/1/${id}`, {
        content,
        location,
        themes: [theme] // Mandando como array de strings
      });
      
      alert("Mote criado!");
      setContent('');
      setLocation('');
      fetchDetails(); // Recarrega a página para mostrar o novo mote
    } catch (error) {
      console.error("Erro ao criar mote:", error);
      alert("Erro ao salvar.");
    }
  };

  if (loading) return <p style={{padding: 20}}>Carregando...</p>;
  if (!entry) return <p style={{padding: 20}}>Livro não encontrado na estante.</p>;

  return (
    <div style={{ padding: '20px', display: 'flex', gap: '40px' }}>
      {/* Coluna da Esquerda: Capa e Info */}
      <div style={{ width: '250px' }}>
        <img 
            src={entry.book.coverUrl || ''} 
            alt={entry.book.title} 
            style={{ width: '100%', borderRadius: '8px', boxShadow: '0 4px 12px rgba(0,0,0,0.15)' }} 
        />
        <h2 style={{ marginTop: '15px', fontSize: '1.5rem' }}>{entry.book.title}</h2>
        <p style={{ color: '#666' }}>{entry.book.author}</p>
        <p style={{ fontSize: '0.9rem', marginTop: '10px' }}>ISBN: {entry.book.isbn}</p>
      </div>

      {/* Coluna da Direita: Motes e Formulário */}
      <div style={{ flex: 1 }}>
        
        {/* Formulário de Criação */}
        <div style={{ background: '#fff', padding: '20px', borderRadius: '8px', boxShadow: '0 2px 8px rgba(0,0,0,0.05)', marginBottom: '30px' }}>
          <h3>Novo Mote ✍️</h3>
          <form onSubmit={handleCreateMote} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <textarea 
              placeholder="O que você pensou ao ler isso?"
              value={content}
              onChange={e => setContent(e.target.value)}
              style={{ padding: '10px', borderRadius: '4px', border: '1px solid #ddd', minHeight: '80px' }}
            />
            <div style={{ display: 'flex', gap: '10px' }}>
              <input 
                type="text" 
                placeholder="Onde? (ex: Pág 42)" 
                value={location}
                onChange={e => setLocation(e.target.value)}
                style={{ flex: 1, padding: '10px', borderRadius: '4px', border: '1px solid #ddd' }}
              />
              <select 
                value={theme} 
                onChange={e => setTheme(e.target.value)}
                style={{ padding: '10px', borderRadius: '4px', border: '1px solid #ddd' }}
              >
                <option value="INSIGHTFUL">💡 Insight</option>
                <option value="FUNNY">😂 Engraçado</option>
                <option value="QUESTION">❓ Dúvida</option>
                <option value="QUOTE">💬 Citação</option>
              </select>
              <button type="submit" style={{ padding: '10px 20px', background: '#3498db', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                Salvar
              </button>
            </div>
          </form>
        </div>

        {/* Lista de Motes */}
        <h3>Suas Anotações</h3>
        {(!entry.motes || entry.motes.length === 0) ? (
          <p style={{ color: '#999' }}>Nenhuma anotação ainda. Seja o primeiro a escrever!</p>
        ) : (
          <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
            {entry.motes.map(mote => (
              <div key={mote.id} style={{ background: '#fff', padding: '15px', borderRadius: '8px', borderLeft: '4px solid #3498db', boxShadow: '0 2px 4px rgba(0,0,0,0.05)' }}>
                <p style={{ fontSize: '1.1rem', margin: '0 0 10px 0' }}>"{mote.content}"</p>
                <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.8rem', color: '#888' }}>
                  <span>📍 {mote.location}</span>
                  <span>🏷️ {mote.themes.join(', ')}</span>
                </div>
              </div>
            ))}
          </div>
        )}

      </div>
    </div>
  );
}