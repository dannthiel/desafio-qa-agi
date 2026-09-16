package br.com.desafioqa.web.support;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    private final Supplier<WebDriver> driverSupplier;

    public ScreenshotOnFailureExtension(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) {
            return;
        }

        WebDriver driver = driverSupplier.get();
        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            return;
        }

        byte[] screenshot;
        try {
            screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
        } catch (RuntimeException ignored) {
            return;
        }
        Allure.addAttachment("Screenshot da falha", "image/png",
                new ByteArrayInputStream(screenshot), ".png");

        Path directory = Path.of("target", "screenshots");
        String safeName = context.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        try {
            Files.createDirectories(directory);
            Files.write(directory.resolve(safeName + ".png"), screenshot,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ignored) {
            // A evidência no Allure continua disponível mesmo se a cópia local falhar.
        }
    }
}
