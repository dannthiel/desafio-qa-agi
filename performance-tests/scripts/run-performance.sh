#!/usr/bin/env bash
set -euo pipefail

plan="${1:-load-test}"
case "$plan" in
  load-test|spike-test) ;;
  *) echo "Plano inválido. Use load-test ou spike-test." >&2; exit 2 ;;
esac

if ! command -v jmeter >/dev/null 2>&1; then
  echo "JMeter não encontrado. Instale o Apache JMeter 5.6.3 e adicione bin ao PATH." >&2
  exit 1
fi

timestamp="$(date -u +%Y%m%dT%H%M%SZ)"
result_dir="performance-tests/results/${plan}-${timestamp}"
mkdir -p "$result_dir"

jmeter -n \
  -t "performance-tests/jmeter/test-plans/${plan}.jmx" \
  -l "${result_dir}/results.jtl" \
  -j "${result_dir}/jmeter.log" \
  -e -o "${result_dir}/html-report" \
  "${@:2}"

echo "Relatório gerado em ${result_dir}/html-report/index.html"
