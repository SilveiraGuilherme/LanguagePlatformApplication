#!/usr/bin/env zsh
set -euo pipefail

cd "$(dirname "$0")"

if [[ -f ".env" ]]; then
  while IFS= read -r line || [[ -n "$line" ]]; do
    [[ -z "$line" ]] && continue
    [[ "$line" == \#* ]] && continue

    key="${line%%=*}"
    value="${line#*=}"

    [[ -z "$key" ]] && continue
    export "$key=$value"
  done < .env
else
  echo "Missing BACKEND/.env file. Create it from .env.example first."
  exit 1
fi

export SPRING_PROFILES_ACTIVE=local
exec ./mvnw spring-boot:run
