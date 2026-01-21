// Define o formato de um Usuário
export interface User {
  id: number;
  username: string;
  email: string;
}

// Define o formato de um Livro (igual ao BookRequestDTO do Java)
export interface Book {
  isbn: string;
  title: string;
  author: string;
  coverUrl: string | null; // Pode vir null do Google
}

// Define o formato de um item na Estante
export interface LibraryEntry {
  id: number;
  user: User;
  book: Book;
  addedAt: string;
}

// Define o formato de um Mote
export interface Mote {
  id: number;
  content: string;
  location: string;
  themes: string[]; // ["FUNNY", "INSIGHTFUL"]
}