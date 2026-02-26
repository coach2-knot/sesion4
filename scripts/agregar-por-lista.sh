#!/bin/bash
# ============================================================================
# Script: agregar-por-lista.sh
# Propósito: Agregar colaboradores desde un archivo de texto (más rápido)
#
# Uso:
#   1. Crea un archivo estudiantes.txt con un username por línea
#   2. Ejecuta: ./scripts/agregar-por-lista.sh OWNER/REPO estudiantes.txt
#
# Ejemplo de estudiantes.txt:
#   juanperez
#   mariagomez
#   carloslopez
#   anaruiz
# ============================================================================

set -e

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "❌ Uso: $0 OWNER/REPO estudiantes.txt"
    exit 1
fi

REPO=$1
FILE=$2

if [ ! -f "$FILE" ]; then
    echo "❌ Archivo no encontrado: $FILE"
    exit 1
fi

echo "📦 Repositorio: $REPO"
echo "📄 Archivo: $FILE"
echo ""

count=0
while IFS= read -r username || [ -n "$username" ]; do
    # Ignorar líneas vacías y comentarios
    username=$(echo "$username" | tr -d '[:space:]')
    if [ -z "$username" ] || [[ "$username" == \#* ]]; then
        continue
    fi

    echo -n "  Agregando $username... "
    if gh api repos/$REPO/collaborators/$username -X PUT -f permission=push 2>/dev/null; then
        echo "✅"
        count=$((count + 1))
    else
        echo "⚠️  Error"
    fi
done < "$FILE"

echo ""
echo "✅ $count invitaciones enviadas"
echo "   Los estudiantes deben aceptar en: https://github.com/$REPO/invitations"
