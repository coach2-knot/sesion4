#!/bin/bash
# ============================================================================
# Script: crear-ramas-grupos.sh
# Propósito: Crear las 8 ramas feature/ci-grupo-X (una por pareja/trío)
#            y hacer push al remoto para que cada grupo tenga su rama lista.
#
# Por qué crear las ramas de antemano?
#   - Los estudiantes solo hacen: git checkout feature/ci-grupo-X
#   - Se evitan errores de tipeo en nombres de ramas
#   - Cada rama ya tiene el código base listo para trabajar
#   - Se ahorra tiempo valioso de clase
#
# Prerequisitos:
#   - Git inicializado y con remoto configurado
#   - GitHub CLI instalado: https://cli.github.com/
#   - Estar en la rama main con el código base
#
# Uso:
#   chmod +x scripts/crear-ramas-grupos.sh
#   ./scripts/crear-ramas-grupos.sh
#
# ============================================================================

set -e

TOTAL_GRUPOS=8

echo ""
echo "═══════════════════════════════════════════════════"
echo "  Creando ramas feature/ci-grupo-X para 8 grupos"
echo "═══════════════════════════════════════════════════"
echo ""

# --- Asegurarse de estar en main y actualizado ---
echo "📌 Asegurando que estamos en main actualizado..."
git checkout main
git pull origin main 2>/dev/null || echo "  (sin cambios remotos)"
echo ""

# --- Crear las 8 ramas ---
for i in $(seq 1 $TOTAL_GRUPOS); do
    BRANCH="feature/ci-grupo-$i"

    # Verificar si la rama ya existe local
    if git show-ref --verify --quiet "refs/heads/$BRANCH" 2>/dev/null; then
        echo "  ⚠️  $BRANCH ya existe localmente, saltando..."
    else
        echo -n "  Creando $BRANCH... "
        git checkout -b "$BRANCH" main --quiet
        git checkout main --quiet
        echo "✅"
    fi
done

echo ""

# --- Push de todas las ramas al remoto ---
echo "═══════════════════════════════════════════════════"
echo "  Subiendo ramas al remoto (origin)"
echo "═══════════════════════════════════════════════════"
echo ""

for i in $(seq 1 $TOTAL_GRUPOS); do
    BRANCH="feature/ci-grupo-$i"
    echo -n "  Push $BRANCH... "

    if git push origin "$BRANCH" --quiet 2>/dev/null; then
        echo "✅"
    else
        echo "⚠️  (puede que ya exista en el remoto)"
    fi
done

echo ""

# --- Volver a main ---
git checkout main --quiet

echo "═══════════════════════════════════════════════════"
echo "  ¡Listo! 8 ramas creadas y publicadas"
echo "═══════════════════════════════════════════════════"
echo ""
echo "📋 Ramas creadas:"
echo ""
for i in $(seq 1 $TOTAL_GRUPOS); do
    if [ $i -eq 8 ]; then
        echo "    feature/ci-grupo-$i  ← Trío"
    else
        echo "    feature/ci-grupo-$i  ← Pareja $i"
    fi
done
echo ""
echo "═══════════════════════════════════════════════════"
echo "  Bloque para pegar en el chat de Teams:"
echo "═══════════════════════════════════════════════════"
echo ""
echo "📋 INSTRUCCIONES - Clonar y moverse a su rama"
echo ""
echo "1. Abrir terminal/Git Bash"
echo "2. Clonar el repo:"
echo "   git clone https://github.com/TU_USUARIO/TU_REPO.git"
echo "   cd TU_REPO"
echo ""
echo "3. Moverse a su rama de grupo:"
for i in $(seq 1 $TOTAL_GRUPOS); do
    echo "   Grupo $i → git checkout feature/ci-grupo-$i"
done
echo ""
echo "4. Verificar: git branch"
echo "   Debe mostrar: * feature/ci-grupo-X"
echo ""
