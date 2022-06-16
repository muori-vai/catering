# Catering
Progetto per Sistemi Informativi sul Web

(Sono riportati solo gli scenari principali di successo)

Caso d'uso 1, 2: Inserimento Chef(Ingrediente); attore primario: Admin
  1. Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale (con le operazioni).
  3. Admin sceglie l'operazione "Inserisci Chef"("Inserisci Ingrediente").
  4. Admin inserisce i dati dello Chef(Ingrediente).
  5. Il Sistema salva lo Chef(Ingrediente).
 
Caso d'uso 3: Inserimento Buffet; attore primario: Admin
  1. Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale (con le operazioni).
  3. Admin sceglie l'operazione "Visualizza Chef".
  4. Il Sistema mostra l'elenco degli Chef.
  5. Admin sceglie lo Chef in cui vuole inserire il Buffet.
  6. Il Sistema mostra i dettagli dello Chef.
  7. Admin sceglie l'operazione "Inserisci Buffet".
  8. Admin inserisce i dati del Buffet.
  9. Il Sistema salva il Buffet.

Caso d'uso 4: Inserimento Piatto; attore primario: Admin
  1. Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale (con le operazioni).
  3. Admin sceglie l'operazione "Visualizza Chef".
  4. Il Sistema mostra l'elenco degli Chef.
  5. Admin sceglie lo Chef al cui Buffet vuole inserire il Piatto.
  6. Il Sistema mostra i dettagli dello Chef (con l'elenco dei suoi Buffet)
  7. Admin sceglie il Buffet in cui vuole inserire il Piatto.
  8. Il Sistema mostra i dettagli del Buffet.
  9. Admin sceglie l'operazione "Inserisci Piatto"
  10. Admin inserisce i dati del Piatto (scegliendo anche tutti gli Ingredienti di cui è composto).
  11. Il Sistema salva il Piatto.

Caso d'uso 5, 6, 7, 8: Cancellazione Chef, Buffet, Piatto, Ingrediente; attore primario: Admin
  1. Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale (con le operazioni).
  3. Admin sceglie l'operazione "Visualizza Chef"("Buffet", "Piatto", "Ingrediente").
  4. Il Sistema mostra l'elenco dell'entità scelta.
  5. Admin sceglie l'elemento dell'entità da cancellare (tramite l'operazione "Cancella").
  6. Il Sistema mostra una pagina di conferma (e in caso di conferma, cancella l'entità).

Caso d'uso 9, 10, 11, 12: Modifica Chef, Buffet, Piatto, Ingrediente; attore primario: Admin
  1. Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale (con le operazioni).
  3. Admin sceglie l'operazione "Visualizza Chef"("Buffet", "Piatto", "Ingrediente").
  4. Il Sistema mostra l'elenco dell'entità scelta.
  5. Admin sceglie l'elemento dell'entità da modificare (tramite l'operazione "Modifica").
  6. Admin inserisce i nuovi dati dell'entità.
  7. Il Sistema aggiorna i dati dell'entità.
 
 Caso d'uso 13, 14, 15, 16: Visualizzazione Chef, Buffet, Piatto, Ingrediente; attore primario: User/Admin
  1. User/Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale (con le operazioni, alcune vengono nascoste se l'attore è lo User).
  3. User/Admin sceglie l'operazione "Visualizza Chef"("Buffet", "Piatto", "Ingrediente").
  4. Il Sistema mostra l'elenco dell'entità scelta.
  5. User/Admin ha anche la possibilità di vedere un'entità nell'elenco in dettaglio (tramite l'operazione "Dettagli").
