param(
    [ValidateSet("load-test", "spike-test")]
    [string]$Plan = "load-test",
    [string[]]$JMeterProperties = @()
)

$jmeter = Get-Command jmeter -ErrorAction SilentlyContinue
if (-not $jmeter) {
    throw "JMeter não encontrado. Instale o Apache JMeter 5.6.3 e adicione bin ao PATH."
}

$timestamp = (Get-Date).ToUniversalTime().ToString("yyyyMMddTHHmmssZ")
$resultDir = "performance-tests/results/$Plan-$timestamp"
New-Item -ItemType Directory -Path $resultDir -Force | Out-Null

$arguments = @(
    "-n",
    "-t", "performance-tests/jmeter/test-plans/$Plan.jmx",
    "-l", "$resultDir/results.jtl",
    "-j", "$resultDir/jmeter.log",
    "-e", "-o", "$resultDir/html-report"
) + $JMeterProperties

& $jmeter.Source $arguments
if ($LASTEXITCODE -ne 0) {
    throw "A execução do JMeter falhou com código $LASTEXITCODE."
}

Write-Host "Relatório gerado em $resultDir/html-report/index.html"
