#!/bin/bash
# =============================================================
# 🧭 Online_Courses_Backend :: Progress Updater
# -------------------------------------------------------------
# Scans all microservice modules and updates PROGRESS_OUTLOOK.md
# with dynamic status, emojis, and last updated timestamp.
# =============================================================

ROOT_DIR=$(dirname "$0")
PROGRESS_FILE="$ROOT_DIR/PROGRESS_OUTLOOK.md"

# Colors for display
GREEN="\033[1;32m"
YELLOW="\033[1;33m"
RED="\033[1;31m"
GRAY="\033[1;90m"
RESET="\033[0m"

echo -e "${YELLOW}🔍 Scanning project modules...${RESET}"

declare -A SERVICE_PORTS=(
  ["user-service"]=9595
  ["course-service"]=9596
  ["lesson-service"]=9597
  ["enrollment-service"]=9598
  ["payment-service"]=9599
  ["progress-service"]=9600
  ["gateway-service"]=9601
)

# detect done / in progress / todo
get_status() {
  local service=$1
  if [ -f "$ROOT_DIR/$service/.done" ]; then
    echo "✅ Done"
  elif [ -f "$ROOT_DIR/$service/.progress" ]; then
    echo "🚧 In Progress"
  else
    echo "⏳ Todo"
  fi
}

# detect visual class
get_class() {
  local status=$1
  case "$status" in
    "✅ Done") echo "done" ;;
    "🚧 In Progress") echo "in_progress" ;;
    "⏳ Todo") echo "todo" ;;
    *) echo "planned" ;;
  esac
}

# update table dynamically
update_markdown() {
  echo "# 🧭 Online_Courses_Backend — Progress Outlook" > "$PROGRESS_FILE"
  echo "" >> "$PROGRESS_FILE"
  echo "> Auto-generated on $(date '+%Y-%m-%d %H:%M:%S')" >> "$PROGRESS_FILE"
  echo "" >> "$PROGRESS_FILE"
  echo "## 📦 Microservice Architecture Overview" >> "$PROGRESS_FILE"
  echo '```mermaid' >> "$PROGRESS_FILE"
  echo "flowchart LR" >> "$PROGRESS_FILE"
  echo "    subgraph G[Online_Courses_Backend]" >> "$PROGRESS_FILE"

  for service in "${!SERVICE_PORTS[@]}"; do
    STATUS=$(get_status "$service")
    CLASS=$(get_class "$STATUS")
    SERVICE_ICON=""

    case "$service" in
      "user-service") SERVICE_ICON="👥 User Service" ;;
      "course-service") SERVICE_ICON="📚 Course Service" ;;
      "lesson-service") SERVICE_ICON="🎓 Lesson Service" ;;
      "enrollment-service") SERVICE_ICON="🪪 Enrollment Service" ;;
      "payment-service") SERVICE_ICON="💳 Payment Service" ;;
      "progress-service") SERVICE_ICON="🧩 Progress Tracker" ;;
      "gateway-service") SERVICE_ICON="🚪 Gateway Service" ;;
    esac

    echo "        ${service//-/_}[$SERVICE_ICON]:::${CLASS}" >> "$PROGRESS_FILE"
  done

  echo "    end" >> "$PROGRESS_FILE"
  echo "" >> "$PROGRESS_FILE"
  echo "    classDef done fill:#00BFA6,stroke:#003F2E,color:#fff;" >> "$PROGRESS_FILE"
  echo "    classDef in_progress fill:#FFD54F,stroke:#7B5E00,color:#000;" >> "$PROGRESS_FILE"
  echo "    classDef todo fill:#FF5252,stroke:#5A0000,color:#fff;" >> "$PROGRESS_FILE"
  echo "    classDef planned fill:#9E9E9E,stroke:#2F2F2F,color:#fff;" >> "$PROGRESS_FILE"
  echo '```' >> "$PROGRESS_FILE"

  echo "" >> "$PROGRESS_FILE"
  echo "## ✅ Development Checklist" >> "$PROGRESS_FILE"
  echo "| Service | Port | Status |" >> "$PROGRESS_FILE"
  echo "|----------|------|---------|" >> "$PROGRESS_FILE"

  for service in "${!SERVICE_PORTS[@]}"; do
    STATUS=$(get_status "$service")
    PORT=${SERVICE_PORTS[$service]}
    NAME=$(echo "$service" | sed 's/-service//; s/-/ /g' | sed 's/\b\(.\)/\u\1/g')
    echo "| $NAME | $PORT | $STATUS |" >> "$PROGRESS_FILE"
  done

  echo "" >> "$PROGRESS_FILE"
  echo "🗓️ **Last Updated:** $(date '+%Y-%m-%d %H:%M:%S')" >> "$PROGRESS_FILE"
  echo "" >> "$PROGRESS_FILE"
  echo "> 💡 Mark a service as done by creating a .done file inside it, or mark as in progress with a .progress file." >> "$PROGRESS_FILE"
}

update_markdown

echo -e "${GREEN}✅ PROGRESS_OUTLOOK.md updated successfully!${RESET}"
