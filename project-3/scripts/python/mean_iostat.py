import sys
import re

# Function to read data from a file
def read_data_from_file(filename):
    try:
        with open(filename, 'r') as file:
            return file.read()
    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
        sys.exit(1)

# Function to calculate average %user and %util for sdd
def calculate_averages(data):
    # Find %user values
    user_pattern = re.compile(r"avg-cpu:\s+%user\s+%nice\s+%system\s+%iowait\s+%steal\s+%idle\s+([\d.]+)")
    user_values = [float(value) for value in user_pattern.findall(data)]
    avg_user = sum(user_values) / len(user_values) if user_values else 0.0
    max_user = max(user_values) if user_values else 0.0
    min_user = min(user_values) if user_values else 0.0

    # Find %util values for sdd
    sde_section_pattern = re.compile(r"sde\s+[\d. ]+\s+(\d+\.\d+)")
    util_values = [float(value) for value in sde_section_pattern.findall(data)]
    avg_util_sde = sum(util_values) / len(util_values) if util_values else 0.0
    max_util_sde = max(util_values) if util_values else 0.0
    min_util_sde = min(util_values) if util_values else 0.0

    return avg_user, max_user, min_user, avg_util_sde, max_util_sde, min_util_sde

# Main script
if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python script_name.py filename")
        sys.exit(1)

    filename = sys.argv[1]
    data = read_data_from_file(filename)
    avg_user, max_user, min_user, avg_util_sde, max_util_sde, min_util_sde = calculate_averages(data)

    print("CPU Utilization:")
    print(f"\tAverage %user: {avg_user:.2f}")
    print(f"\tMaximum %user: {max_user:.2f}")
    print(f"\tMinimum %user: {min_user:.2f}")
    print("Disk Utilization:")
    print(f"\tAverage %util for disk: {avg_util_sde:.2f}")
    print(f"\tMaximum %util for disk: {max_util_sde:.2f}")
    print(f"\tMinimum %util for disk: {min_util_sde:.2f}")
