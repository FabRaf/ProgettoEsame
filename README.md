# Progetto d'esame - appello del 22/07/2019
Il presente progetto riguarda la gestione di dati economici di diversi paesi europei negli anni dal 2007 al 2016. Esso è realizzato in linguaggio Java e prevede il download di un dataset di riferimento, la memorizzazione dei dati in formato JSON e la possibilità di richiedere, tramite server locale, i dati stessi, i metadati, le statistiche sui dati.
## Struttura
Le classi di cui si compone il progetto sono suddivise nei seguenti package secondo un criterio logico.

 - `com.esame.progetto`: 
	 - `ProgettoEsameApplication`: classe principale, contentente il metodo `main()`, si occupa di chiamare il download del dataset e di avviare il server;
	 - `Download`: gestisce il download del dataset;
	 - `Controller`: gestisce le richieste al server.
 - `dataset`:
 	 - `StatoMembro`: definisce la struttura che rappresenta un record del dataset;
	 - `GeneratoreLista`: genera la lista di oggetti rappresentante l'intero dataset.
 - `operazioni`:
 	 - `GeneratoreMetadati`: genera la lista di oggetti contenenti i metadati;
 	 - `Statistiche`: genera statistiche sugli attributi del dataset.

## Funzionamento
All'avvio dell'applicazione viene eseguito il download del dataset di riferimento tramite l'URL rintracciato all'interno del JSON ottenibile all'indirizzo http://data.europa.eu/euodp/data/api/3/action/package_show?id=funds-absorption-rate.
Tale dataset prevede i seguenti campi: Member State, Year, Fund, Total Paid, Advance, Interim, Absorption (%), dove i primi tre sono gestiti dall'applicazione come variabili di tipo String, mentre i rimanenti come variabili di tipo double.
Successivamente viene avviato un server web locale sulla porta 8080 ricorrendo all'utilizzo del framework Spring. Tramite tale server sarà possibile effettuare le seguenti richieste GET.

`localhost:8080`
 - `/data`: restituisce i dati, ovvero gli oggetti JSON corrispondenti ai record del dataset;
 - `/metadata`: restituisce i metadati, ovvero l'elenco dei nomi degli attributi di ogni record e del tipo e del nome delle variabili con cui sono
   memorizzati nell'applicazione;
 - `/stats`: restituisce alcune statistiche sui dati.

Si osservi che tutte le chiamate appena citate, se eseguite su attributi di tipo numerico, producono risultati che sono, per tutti i campi eccetto "absorption", in notazione esponenziale, in quanto eccedono il valore 10^7, limite superiore oltre il quale Java ricorre a tale notazione. Utilizzando il browser Mozilla Firefox è possibile visualizzare i risultati nella scrittura standard.
## Implementazione delle statistiche
Meritevole di approfondimento è la modalità con cui vengono implementate le statistiche. Esse si differenziano in base al tipo dell'attributo cui si fa riferimento: su attributi di tipo String sarà possibile ottenere per ogni campo il numero di occorrenze nell'intero dataset, mentre su attributi di tipo
numerico (nel presente caso tutti double) sarà possibile ottenere la media (avg), il minimo (min), il massimo (max), la deviazione standard (dev std),
la somma (sum) e il conteggio (count).
L'attributo va specificato nella richiesta delle statistiche aggiungendo un parametro la cui chiave è "field" e il cui valore può essere "memberState", "year", "fund", "totalPaid", "advance", "interim", "absorption", corrispondenti rispettivamente ai campi nominati in precedenza.

Pertanto un esempio di richiesta di statistiche per un attributo di tipo String è: `localhost:8080/stats?field=memberState`, che restituisce l'elenco di tutti i campi "Member State" del dataset, ognuno con relativo numero di occorrenze.
Un esempio di richiesta di statistiche per un attributo di tipo numerico, invece, è: `localhost:8080/stats?field=advance`, che restituisce le statistiche numeriche prima descritte (media, min, ecc.) calcolate su tutti i valori corrispondenti al campo "advance" relativi a ogni record del dataset.

### Gestione dei problemi in fase di richiesta
L'applicazione prevede la gestione di eventuali problemi in fase di richiesta delle statistiche: se il parametro specificato ha una chiave che differisce da "field" viene inserito nell'oggetto JSON il messaggio `"errore": "specificare un parametro di tipo 'field'"`; se, invece, il campo inserito non corrisponde ad alcuno di quelli previsti il messaggio che verrà inserito è `"errore": "campo inesistente"`.
## Esempi di test
Sulla base di quanto detto è possibile eseguire dei test di funzionamento effettuando le richieste:
 - [http://localhost:8080/data](http://localhost:8080/data)
 - [http://localhost:8080/metadata](http://localhost:8080/metadata)
 - [http://localhost:8080/stats?field=memberState](http://localhost:8080/stats?field=memberState)
 - [http://localhost:8080/stats?field=year](http://localhost:8080/stats?field=year)
 - [http://localhost:8080/stats?field=fund](http://localhost:8080/stats?field=fund)
 - [http://localhost:8080/stats?field=totalPaid](http://localhost:8080/stats?field=totalPaid)
 - [http://localhost:8080/stats?field=advance](http://localhost:8080/stats?field=advance)
 - [http://localhost:8080/stats?field=interim](http://localhost:8080/stats?field=interim)
 - [http://localhost:8080/stats?field=absorption](http://localhost:8080/stats?field=absorption)

Per avere una prova del comportamento dell'applicazione nel caso di richieste di statistiche errate si possono utilizzare le rotte (volutamente sbagliate):

 - [http://localhost:8080/stats?Fild=memberState](http://localhost:8080/stats?Fild=memberState)
 - [http://localhost:8080/stats?field=Member_state](http://localhost:8080/stats?field=Member_state)
## Informazioni aggiuntive
Nell'omonima cartella sono riportati i diagrammi UML del progetto. In particolare si tratta del diagramma dei casi d'uso, del diagramma delle classi e del diagramma delle sequenze.
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzAzNzYzMDAsLTIwNTAzNzAzMzMsMTUyOD
c2MTU3MSw0OTk3MTg1NzUsNzc2MTgyMzU0LDEwODY2MTUxNDgs
MTIwODA3Njk2MSw4NzgxOTQ0OTgsLTEyNTE3NzM2NzgsMTcwNT
E1NTk4MiwzMDQyNTAwMTYsMTEzNjM3MzU1MywtNTMwNjIwODgx
LDIwNjk5NDQ0NTgsLTMzNzYyMjc1OCwtMTgyMDgwMzg2OCwtMT
A1NjQ1MTk3OCw5NDM1NDQ2MjAsLTIxMTkxODY3NDIsLTEwNzY5
NDcxMjBdfQ==
-->