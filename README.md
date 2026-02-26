# Selenium CI/CD - Sesion 4

Proyecto base para la Sesion 4 del curso **QC Automation** de Knot Academy x Accenture.

## Que incluye

- Tests de Selenium con Page Object Model contra **dos sitios**:
  - **SauceDemo** (login, carrito) — 6 tests
  - **The Internet Herokuapp** (login, frames, dropdown, checkboxes, dynamic loading) — 18 tests
- GitHub Actions workflow para CI automatizado
- Allure Reports para dashboard visual de resultados
- Scripts para configurar colaboradores y branch protection

## Requisitos

- Java 17+
- Maven 3.9+
- Chrome instalado
- GitHub CLI (`gh`) para scripts de setup

## Ejecutar tests localmente

```bash
# Todos los tests (Chrome con ventana visible)
mvn test

# Solo tests de SauceDemo
mvn test -Dtest="SauceDemoLoginTest,SauceDemoCartTest"

# Solo tests de Heroku
mvn test -Dtest="com.knotacademy.qc.selenium.tests.heroku.*"

# Solo un test especifico
mvn test -Dtest="FramesTest"

# Chrome headless (como en CI)
mvn test -Dbrowser=chrome -Dheadless=true

# Firefox
mvn test -Dbrowser=firefox
```

## Pipeline CI/CD

El workflow `.github/workflows/selenium-ci.yml` ejecuta automaticamente en cada push y PR:

1. Setup Java 17
2. Cache de Maven
3. Tests Selenium en Chrome headless (24 tests contra 2 sitios)
4. Publica reportes Surefire + Allure como artifacts

## Allure Reports (opcional)

Despues de ejecutar tests:

```bash
npx allure serve target/allure-results
```

## Mapa de tests

| Sitio | Clase | Tests | Que cubre |
|-------|-------|-------|-----------|
| SauceDemo | SauceDemoLoginTest | 3 | Login valido, credenciales invalidas, usuario bloqueado |
| SauceDemo | SauceDemoCartTest | 3 | Agregar 1 producto, agregar 2, verificar pagina de carrito |
| Heroku | HerokuLoginTest | 4 | Login valido, password erronea, usuario inexistente, logout |
| Heroku | FramesTest | 4 | Cargar pagina, leer texto en iframe, escribir en iframe, volver al main |
| Heroku | DropdownTest | 3 | Contar opciones, seleccionar opcion, cambiar seleccion |
| Heroku | CheckboxesTest | 4 | Contar checkboxes, estado default, check primero, uncheck segundo |
| Heroku | DynamicLoadingTest | 3 | Elemento oculto que se revela, elemento dinamico, boton Start visible |

**Total: 24 tests**

## Estructura del proyecto

```
src/test/java/com/knotacademy/qc/selenium/
  config/              ConfigLoader.java
  core/                BaseTest.java, DriverFactory.java
  pages/
    BasePage.java, LoginPage.java, InventoryPage.java, CartPage.java
    heroku/            FramesPage, DropdownPage, CheckboxesPage,
                       DynamicLoadingPage, HerokuLoginPage
  tests/
    SauceDemoLoginTest.java, SauceDemoCartTest.java
    heroku/            HerokuLoginTest, FramesTest, DropdownTest,
                       CheckboxesTest, DynamicLoadingTest
src/test/resources/
  config.properties
.github/workflows/
  selenium-ci.yml
scripts/
  setup-colaboradores.sh
  agregar-por-lista.sh
  estudiantes.txt
```

## Setup para el coach

### Agregar estudiantes como colaboradores

```bash
# Opcion 1: Interactivo (uno por uno)
chmod +x scripts/setup-colaboradores.sh
./scripts/setup-colaboradores.sh TU_USUARIO/selenium-ci-cd-sesion4

# Opcion 2: Desde archivo (mas rapido)
# 1. Edita scripts/estudiantes.txt con los usernames
# 2. Ejecuta:
chmod +x scripts/agregar-por-lista.sh
./scripts/agregar-por-lista.sh TU_USUARIO/selenium-ci-cd-sesion4 scripts/estudiantes.txt
```

---

**Knot Academy x Accenture** | Coach: Arnold Ramirez
