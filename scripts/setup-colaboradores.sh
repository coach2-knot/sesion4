#!/bin/bash
# ============================================================================
# Script: setup-colaboradores.sh
# Propósito: Agregar estudiantes como colaboradores al repo y configurar
#            branch protection para la dinámica de PR en Sesión 4.
#
# Prerequisitos:
#   - GitHub CLI instalado: https://cli.github.com/
#   - Autenticado con: gh auth login
#   - Ser owner del repositorio
#
# Uso:
#   chmod +x scripts/setup-colaboradores.sh
#   ./scripts/setup-colaboradores.sh TU_USUARIO/TU_REPO
#
# ============================================================================

set -e

# --- Validar argumento ---
if [ -z "$1" ]; then
    echo "❌ Uso: $0 OWNER/REPO"
    echo "   Ejemplo: $0 aramirez-knot/selenium-ci-cd-sesion4"
    exit 1
fi

REPO=$1
echo "📦 Repositorio: $REPO"
echo ""

# ============================================================================
# PASO 1: Agregar colaboradores
# ============================================================================
echo "═══════════════════════════════════════════"
echo "  PASO 1: Agregar estudiantes como colaboradores"
echo "═══════════════════════════════════════════"
echo ""
echo "Agrega los usernames de GitHub de tus estudiantes,"
echo "uno por línea. Cuando termines, escribe 'listo':"
echo ""

STUDENTS=()
while true; do
    read -p "  GitHub username (o 'listo'): " username
    if [ "$username" == "listo" ] || [ "$username" == "LISTO" ]; then
        break
    fi
    if [ -n "$username" ]; then
        STUDENTS+=("$username")
    fi
done

echo ""
echo "📋 Estudiantes a agregar: ${#STUDENTS[@]}"
echo ""

for student in "${STUDENTS[@]}"; do
    echo -n "  Agregando $student... "
    if gh api repos/$REPO/collaborators/$student -X PUT -f permission=push 2>/dev/null; then
        echo "✅ Invitación enviada"
    else
        echo "⚠️  Error (puede que ya sea colaborador)"
    fi
done

echo ""
echo "✅ Invitaciones enviadas. Los estudiantes deben aceptar en su email o en:"
echo "   https://github.com/$REPO/invitations"
echo ""

# ============================================================================
# PASO 2: Configurar Branch Protection en main
# ============================================================================
echo "═══════════════════════════════════════════"
echo "  PASO 2: Configurar Branch Protection en main"
echo "═══════════════════════════════════════════"
echo ""

read -p "¿Configurar branch protection en main? (s/n): " confirm
if [ "$confirm" == "s" ] || [ "$confirm" == "S" ]; then
    echo -n "  Configurando protección de main... "

    gh api repos/$REPO/branches/main/protection -X PUT \
        -H "Accept: application/vnd.github+json" \
        --input - <<EOF 2>/dev/null && echo "✅ Configurado" || echo "⚠️  Error (puede requerir plan Team/Pro)"
{
    "required_status_checks": {
        "strict": true,
        "contexts": ["build-and-test"]
    },
    "enforce_admins": false,
    "required_pull_request_reviews": {
        "required_approving_review_count": 1,
        "dismiss_stale_reviews": false
    },
    "restrictions": null
}
EOF

    echo ""
    echo "  Reglas configuradas:"
    echo "  ✅ CI (build-and-test) debe pasar antes de merge"
    echo "  ✅ Se requiere 1 aprobación en PR"
    echo "  ✅ El admin (tú) puede hacer merge aunque falle CI (para emergencias)"
else
    echo "  Saltando branch protection."
fi

echo ""
echo "═══════════════════════════════════════════"
echo "  ¡Setup completo!"
echo "═══════════════════════════════════════════"
echo ""
echo "📌 Próximos pasos:"
echo "  1. Los estudiantes aceptan la invitación"
echo "  2. Cada uno clona: git clone https://github.com/$REPO.git"
echo "  3. Crean su branch: git checkout -b feature/ci-<nombre>"
echo "  4. Hacen push y abren PR"
echo ""
