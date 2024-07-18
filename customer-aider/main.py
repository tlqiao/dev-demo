import sys
import os
from repomap import RepoMap
from utils import find_src_files

def main():
    rm = RepoMap(root=".")
    print("Welcome to Aider!")

    while True:
        command = input("Enter command (map, explain, modify, quit): ").strip().lower()
        if command == "map":
            chat_fnames = []
            other_fnames = input("Enter directory or file to map: ").split()
            for fname in other_fnames:
                if os.path.isdir(fname):
                    chat_fnames += find_src_files(fname)
                else:
                    chat_fnames.append(fname)
            repo_map = rm.get_repo_map(chat_fnames, [])
            for file, score in repo_map:
                print(f"{file}: {score:.4f}")
        elif command == "explain":
            fname = input("Enter file to explain: ").strip()
            content = rm.explain_file(fname)
            print(content)
        elif command == "modify":
            fname = input("Enter file to modify: ").strip()
            print("Current content:")
            print(rm.explain_file(fname))
            changes = input("Enter new content: ")
            result = rm.modify_file(fname, changes)
            print(result)
        elif command == "quit":
            break

if __name__ == "__main__":
    main()
