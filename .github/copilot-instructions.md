## Quick orientation for AI coding agents

This repository implements a small multi-service app for trading Pokemon TCG cards. Below are the concrete patterns, commands, and files that make an agent productive immediately.

### Big picture
- Backend: Java Spring Boot app under `backend/`. Main class: `backend/src/main/java/com/chiertu/ptcg_pocket_trading_backend/PtcgPocketTradingBackendApplication.java`.
- Frontend: `frontend/` (has a `Dockerfile`).
- Scraper: `scraper/` (Python; `requirements.txt` present).
- Dev composition: root `compose.yml` and per-service `Dockerfile`s (backend, frontend, postgres, scraper).
- Data: CSVs and images in `data/` (e.g. `data/card_details_pokemon.csv`, `data/image/*`). CSVs are loaded by a Spring `CommandLineRunner` on backend startup.

### How to build & run (discovered from the repo)
- Build/run backend locally (Maven wrapper shipped):
  - Build: `cd backend && ./mvnw clean package`
  - Run:  `cd backend && ./mvnw spring-boot:run`
  - Notes: the backend contains a `CsvLoaderRunner` (`backend/src/main/java/.../bootstrap/CsvLoaderRunner.java`) that reads `data/card_details_*.csv` from the classpath and calls the CSV loaders at startup.
- Docker compose (multi-service): `docker compose -f compose.yml up --build` (file is at repo root named `compose.yml`).
- Scraper: `scraper/requirements.txt` lists Python deps. Use a Python venv and `pip install -r scraper/requirements.txt`.

### Project-specific conventions and gotchas
- Package name quirk: the original Java package name contained dashes; the project uses `com.chiertu.ptcg_pocket_trading_backend` (underscores) — see `backend/HELP.md` for details. Use that package when searching or adding classes.
- CSV bootstrapping: loaders live under `backend/src/main/java/.../bootstrap/`:
  - `PokemonCardCsvLoader.java` parses `data/card_details_pokemon.csv` using Apache Commons CSV.
  - `CsvLoaderRunner` is a `CommandLineRunner` that resolves `data/...` from the classpath and calls loaders. Expect CSVs to be on the backend classpath or bundled into resources for local runs.
- Controller patterns: REST controllers live under `backend/src/main/java/.../controller/`. Example: `TradeRestController.java` exposes `GET /trades` and returns a `List<PokemonCard>` — controllers return domain entities directly in current code.
- Data model: domain entities and value objects are under `backend/src/main/java/.../domain/` and `.../domain/value/` (see enums like `CardRarity`, `CardStage`, etc.). Use these packages when adding logic.

### Integration points & external dependencies
- Database: a `postgres/` directory with a `Dockerfile` exists. Local CI/dev often runs Postgres via container (check `compose.yml`).
- External image/metadata APIs considered (in README): `pokemontcg.io`, `tcgdex.dev`. Scraper or image-matching code may integrate with those.
- CSVs and images: `data/` holds authoritative card CSVs and an `image/` folder — use `data` as the canonical source for initial card DB state.

### Small examples to copy-paste
- Add a new controller: create `backend/src/main/java/com/chiertu/ptcg_pocket_trading_backend/controller/MyController.java`, annotate with `@RestController` and `@RequestMapping("/my")`. Follow shape of `TradeRestController.java`.
- Load an extra CSV: add a new loader under `bootstrap/` and register a call from `CsvLoaderRunner` to load `data/myfile.csv` on startup.

### Useful files to inspect for more detail
- `backend/HELP.md` — repository-specific notes (package name quirk).
- `backend/pom.xml` and `backend/mvnw` — build and dependency management.
- `backend/src/main/java/.../bootstrap/CsvLoaderRunner.java` — shows CSV import at startup.
- `backend/src/main/java/.../controller/TradeRestController.java` — example controller and route naming.
- `data/` — CSVs and images; `card_details_pokemon.csv` and `card_details_trainer.csv` are the main inputs.

If you'd like, I can tighten these into a shorter checklist for live editing tasks (e.g., how to add an endpoint + unit test + update CSV import). Anything unclear or another pattern you want emphasized? 
