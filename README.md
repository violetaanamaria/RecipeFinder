# RecipeFinder

Draica Violeta Ana-Maria, AAC, github: https://github.com/violetaanamaria/RecipeFinder 

## Descriere generală

Recipe Finder este o aplicație Android dezvoltată în Kotlin utilizând Jetpack Compose și Material 3 ca toolkit UI. Scopul aplicației este de a permite utilizatorilor să caute rețete după ingrediente, să vizualizeze detalii despre rețete și să salveze rețetele preferate local folosind o bază de date Room.

## Funcționalități principale

- **Căutare rețete** după ingredient sau nume (prin API-ul [TheMealDB](https://www.themealdb.com)).
- **Afișare detalii rețetă**: imagine, instrucțiuni, listă ingrediente + cantități.
- **Favorite**: adăugare / eliminare rețete în/din lista de favorite.
- **Dark Mode**: activare/dezactivare mod întunecat din Settings.
- **Ștergere favorite**: buton în Settings pentru ștergerea completă a rețetelor favorite (Clear all favorites).

- **Persistență locală** a rețetelor favorite cu Room Database.

- **Navigare** prin meniul lateral între:
  - Search
  - Favorites
  - Settings

## Tehnologii folosite

- **Kotlin** ca limbaj principal de programare
- **Jetpack Compose** pentru UI declarativ modern
- **Material 3 (Material You)** pentru design vizual adaptiv și modern
- **ViewModel + StateFlow** pentru gestionarea reactivă a stării
- **Kotlin Coroutines** pentru programare asincronă
- **Retrofit** pentru utilizarea API-ului REST
- **Gson Converter** pentru parsarea automată a răspunsurilor JSON
- **Room** pentru stocarea rețetelor favorite local, folosind o bază de date SQL
- **Coil** pentru încărcarea eficientă a imaginilor din rețea

## Arhitectură

Aplicația este structurată folosind arhitectura **MVVM (Model-View-ViewModel)**, o abordare recomandată în dezvoltarea Android pentru separarea responsabilităților și o mai bună testabilitate:

- **Model**: include clasele de date (`Meal`, `FavoriteMeal`), clasa DAO pentru accesul la baza de date (`MealDao`) și `MealApiService` pentru interacțiunea cu API-ul extern.
- **ViewModel**: `MealViewModel` gestionează logica de business și starea ecranului pentru rețete, iar `SettingsViewModel` controlează stările aplicației precum tema (dark/light)
- **View**: este alcătuită din componente Composable definite în `ui/screens`, care observă datele din ViewModel și se actualizează automat în funcție de acestea.

## Endpoint-uri folosite

- `GET https://www.themealdb.com/api/json/v1/1/search.php?s={nume}`  
  Caută rețete după nume.  
  Exemplu: `https://www.themealdb.com/api/json/v1/1/search.php?s=pizza`
## Bază de date locală

- Se folosește Room pentru persistarea rețetelor favorite.
- Tabel: `favorites` definit prin `@Entity`
- Operații:
  - Inserare rețetă favorită
  - Ștergere rețetă după ID
  - Ștergere toate favoritele
  - Interogare toate favoritele

## Cum rulezi aplicația

1. Clonează acest repo:
   ```bash
   git clone https://github.com/violetaanamaria/RecipeFinder.git
   ```
2. Deschide proiectul în Android Studio.
3. Asigură-te că ai internet activ pentru a apela API-ul.
4. Rulează aplicația pe un emulator sau dispozitiv fizic.
