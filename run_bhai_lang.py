import sys
import subprocess
from pathlib import Path
import shutil


def main():
    """
    Compile and run the BhaiLang interpreter from the project root.

    Behavior:
        - Compiles all .java files under src/main/java recursively
        - Places .class files in the 'out/' directory at root
        - Runs the main BhaiLang class
    """
    try:
        root = Path(__file__).parent.resolve()  # Root folder
        src_folder = root / "src" / "main" / "java"

        # Find all .java files recursively
        java_files = [str(p) for p in src_folder.rglob("*.java")]
        if not java_files:
            print("No Java files found to compile!")
            return

        out_folder = root / "out"
        out_folder.mkdir(exist_ok=True)

        # Compile all Java files
        compile_result = subprocess.run(["javac", "-d", str(out_folder)] + java_files)
        if compile_result.returncode != 0:
            print("Compilation failed!")
            return

        # Main class path
        main_class = "io.github.journeycodesayush.javabhailang.BhaiLang"

        # Run the program
        if len(sys.argv) > 1:
            script = sys.argv[1]
            print(f"Running BhaiLang script: {script}")
            subprocess.run(["java", "-cp", str(out_folder), main_class, script])
        else:
            subprocess.run(
                ["java", "-cp", str(out_folder), main_class],
                stdin=sys.stdin,
                stdout=sys.stdout,
                stderr=sys.stderr,
            )

    except KeyboardInterrupt:
        print("\nKeyboard interrupted... exiting.")

    except subprocess.CalledProcessError:
        print("Compilation or execution failed. Check your Java code.")

    except Exception as e:
        print(f"Some error occurred: {e}")

    finally:
        # Clean up all .class files
        # for file in Path(".").rglob("*.class"):
        #     try:
        #         os.remove(file)
        #     except OSError:
        #         pass
        # shutil.rmtree(out_folder)
        print("Kuch nahi bas sara kaam ho gaya...")


if __name__ == "__main__":
    main()
